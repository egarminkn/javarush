package ru.javarush.tasks.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.javarush.tasks.crud.model.Page;
import ru.javarush.tasks.crud.model.entities.User;
import ru.javarush.tasks.crud.services.UserService;
import ru.javarush.tasks.crud.validators.PageValidator;
import ru.javarush.tasks.crud.validators.UserFormValidator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by eGarmin
 */
@Controller
public class UserController {

    // Подключить работу с БД
    @Autowired
    private UserService userService;

    // Подключить валидаторы
    @Autowired
    private UserFormValidator userFormValidator;
    @Autowired
    private PageValidator pageValidator;
    @InitBinder("userForm")
    protected void initUserBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }
    @InitBinder("page")
    protected void initPageBinder(WebDataBinder binder) {
        binder.setValidator(pageValidator);
    }

    // Главная страница просто пробрасывает на список юзеров
    @RequestMapping(value = "/mvc/crud", method = RequestMethod.GET)
    public String crudIndex(Model model) {
        return "redirect:/mvc/crud/users";
    }

    // Список юзеров
    @RequestMapping(value = "/mvc/crud/users", method = RequestMethod.GET)
    public String showPage(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           Model model) {
        fillModelForListOrFilterPage(null, pageNumber, model);
        return "ru/javarush/tasks/crud/list";
    }

    // Удаление пользователя
    // TODO хорошо бы сделать через RestController
    @RequestMapping(value = "/mvc/crud/users/{id}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") int id,
                             @RequestParam(value = "pageNumber") int pageNumber,
                             @RequestParam(value = "name", required = false) String name,
                             final RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Пользователь удален успешно!");
        if (name == null) {
            return "redirect:/mvc/crud/users?pageNumber=" + pageNumber;
        }
        return "redirect:/mvc/crud/users/by?name=" + URLEncoder.encode(name, "UTF-8") + "&pageNumber=" + pageNumber;
    }

    // Показать форму создания юзера
    @RequestMapping(value = "/mvc/crud/users/add", method = RequestMethod.GET)
    public String showAddUserForm(Model model) {
        User user = new User();
        model.addAttribute("userForm", user);
        model.addAttribute("isAddOperation", true);
        return "ru/javarush/tasks/crud/userform";
    }

    // Показать форму обновления юзера
    @RequestMapping(value = "/mvc/crud/users/{id}/update", method = RequestMethod.GET)
    public String showUpdateUserForm(@PathVariable("id") int id,
                                     @RequestParam(value = "pageNumber") int pageNumber,
                                     @RequestParam(value = "name", required = false) String name,
                                     Model model) {
        User user = userService.findById(id);
        model.addAttribute("userForm", user);
        model.addAttribute("isAddOperation", false);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("nameFilter", name);
        return "ru/javarush/tasks/crud/userform";
    }

    // Кнопка Создать/Обновить на странице создания/обновления пользователя
    // Создать в БД нового или обновить существующего пользователя, а потом вернуться
    // на главную страницу с показом плашки об успешно завершенной операции
    @RequestMapping(value = "/mvc/crud/users", method = RequestMethod.POST) // такой уже был, но с методом GET
    public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
                                   BindingResult result,
                                   @RequestParam(value = "isAddOperation") boolean isAddOperation,
                                   @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                   @RequestParam(value = "nameFilter", required = false) String name,
                                   Model model,
                                   final RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        if (result.hasErrors()) {
            // Model пробрасывается вместе с ошибками автоматически,
            // а вот доп. поля типа isAddOperation нужно класть руками
            model.addAttribute("isAddOperation", isAddOperation);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("nameFilter", name);
            return "ru/javarush/tasks/crud/userform";
        } else {
            userService.createOrUpdate(user);

            redirectAttributes.addFlashAttribute("css", "success");
            if (isAddOperation) {
                redirectAttributes.addFlashAttribute("msg", "Пользователь создан успешно!");
                return "redirect:/mvc/crud/users?pageNumber=" + userService.pageCount();
            }
            redirectAttributes.addFlashAttribute("msg", "Пользователь обновлен успешно!");
            if (name == null) {
                return "redirect:/mvc/crud/users?pageNumber=" + pageNumber;
            }
            return "redirect:/mvc/crud/users/by?name=" + URLEncoder.encode(name, "UTF-8") + "&pageNumber=" + pageNumber;
        }
    }

    // Фильтрация по имени
    @RequestMapping(value = "/mvc/crud/users/by", method = RequestMethod.GET)
    public String filterByName(@RequestParam(value = "name") String name,
                               @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               Model model,
                               final RedirectAttributes redirectAttributes) {
        if (fillModelForListOrFilterPage(name, pageNumber, model)) {
            model.addAttribute("nameFilter", name);
            return "ru/javarush/tasks/crud/list";
        } else {
            redirectAttributes.addFlashAttribute("css", "warning");
            redirectAttributes.addFlashAttribute("msg", "По Вашему запросу ничего не найдено!");
            return "redirect:/mvc/crud/users";
        }
    }

    /**
     * Вспомогательные методы
     */
    /**
     * @return возвращает удалось ли заполнить список
     */
    private boolean fillModelForListOrFilterPage(String name, int pageNumber, Model model) {
        int pageCount = userService.pageCount(name);
        if (pageCount > 0) {
            if (pageNumber < 1) {
                pageNumber = 1;
            } else if (pageNumber > pageCount) {
                pageNumber = pageCount;
            }
            List<User> users = userService.findPage(name, pageNumber);
            model.addAttribute("users", users); // список пользователей на запрашиваемой странице
            model.addAttribute("page", new Page(pageNumber, userService.pageCount(name))); // номер страницы и общее их количество
            return true;
        } else {
            model.addAttribute("page", new Page(1, 1));
            return false;
        }
    }

}