package ru.javarush.tasks.crud.services;

import ru.javarush.tasks.crud.model.entities.User;
import java.util.List;

/**
 * Created by eGarmin
 */
public interface UserService {

    User findById(int id);
    List<User> findByName(String name, int pageNumber);
    List<User> findPage(int pageNumber);
    void createOrUpdate(User user);
    void delete(User user);
    void delete(int id);
    long pageCount();

}
