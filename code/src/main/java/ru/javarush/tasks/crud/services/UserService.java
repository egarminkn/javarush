package ru.javarush.tasks.crud.services;

import ru.javarush.tasks.crud.model.entities.User;
import java.util.List;

/**
 * Created by eGarmin
 */
public interface UserService {

    User findById(int id);
    void createOrUpdate(User user);
    void delete(User user);
    void delete(int id);

    int pageCount();
    int pageCount(String name);

    List<User> findPage(int pageNumber);
    List<User> findPage(String name, int pageNumber);

}
