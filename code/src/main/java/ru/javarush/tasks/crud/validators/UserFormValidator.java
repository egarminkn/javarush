package ru.javarush.tasks.crud.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.javarush.tasks.crud.model.entities.User;
import ru.javarush.tasks.crud.services.UserService;

/**
 * Created by eGarmin
 */
@Component
public class UserFormValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    /**
     * Когда со страница летят поля Model-и, то летят строки, даже если поля модели - это числа или даты
     * Эти поля парсятся на стороне сервера в поля Model-и (в данном случае класс User)
     * Правила парсинга можно задать в классе Model-и с помощью spring-овых аннотаций типа @DateTimeFormat
     * Сюда Model приходит в аргумент "o", а ошибки парсинга в аргумент "errors"
     * Нутро метода призвано добавить к ошибкам парсинга набор бизнес-ошибок валидации
     */
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",  "notEmpty.user.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age",   "notEmpty.user.age");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "admin", "notEmpty.user.isAdmin");

        if (user.getName() != null && user.getName().matches(".*\\d.*")){
            errors.rejectValue("name", "notDigit.user.name");
        }

        if (user.getAge() != null && user.getAge() <= 0){
            errors.rejectValue("age", "positive.user.age");
        }
    }

}