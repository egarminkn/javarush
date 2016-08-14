package ru.javarush.tasks.crud.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.javarush.tasks.crud.model.Page;

/**
 * Created by eGarmin
 */
@Component
public class PageValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Page.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        // Валидировать Page не надо, но валидатор нужен, т.к. я буду Page передавать в model,
        // а это приведет к автоматическим попыткам валидации первым попавшимся валидатором,
        // которым в данном случае является UserFormValidator
    }

}