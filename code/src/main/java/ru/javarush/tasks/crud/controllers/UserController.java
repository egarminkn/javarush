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
    public String showPage(@RequestParam(value="pageNumber", required=false, defaultValue="1") int pageNumber,
                           Model model) {
        model.addAttribute("users", userService.findPage(pageNumber)); // список пользователей на запрашиваемой странице
        model.addAttribute("page", new Page(pageNumber, userService.pageCount())); // номер страницы и общее их количество
        return "ru/javarush/tasks/crud/list";
    }

    // Удаление пользователя
    // TODO хорошо бы сделать через RestController
    @RequestMapping(value = "/mvc/crud/users/{id}/delete", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") int id,
                             final RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("css", "success");
        redirectAttributes.addFlashAttribute("msg", "Пользователь удален успешно!");
        return "redirect:/mvc/crud/users";
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
                                     Model model) {
        User user = userService.findById(id);
        model.addAttribute("userForm", user);
        model.addAttribute("isAddOperation", false);
        return "ru/javarush/tasks/crud/userform";
    }

    // Кнопка Создать/Обновить на странице создания/обновления пользователя
    // Создать в БД нового или обновить существующего пользователя, а потом вернуться
    // на главную страницу с показом плашки об успешно завершенной операции
    @RequestMapping(value = "/mvc/crud/users", method = RequestMethod.POST) // такой уже был, но с методом GET
    public String saveOrUpdateUser(@ModelAttribute("userForm") @Validated User user,
                                   BindingResult result,
                                   @RequestParam(value = "isAddOperation") boolean isAddOperation,
                                   Model model,
                                   final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // Model пробрасывается вместе с ошибками автоматически,
            // а вот доп. поля типа isAddOperation нужно класть руками
            model.addAttribute("isAddOperation", isAddOperation);
            return "ru/javarush/tasks/crud/userform";
        } else {
            userService.createOrUpdate(user);

            redirectAttributes.addFlashAttribute("css", "success");
            if (isAddOperation) {
                redirectAttributes.addFlashAttribute("msg", "Пользователь создан успешно!");
            } else {
                redirectAttributes.addFlashAttribute("msg", "Пользователь обновлен успешно!");
            }

            // FORWARD нельзя, т.к. нет выборки, т.е. нечего показывать
            // POST / FORWARD / GET
            // return "ru/javarush/tasks/crud/list";

            // POST / REDIRECT / GET
            // Сделаем выборку и покажет ее вместе с Flash аттрибутами
            return "redirect:/mvc/crud/users";
        }
    }

}