package ru.javarush.tasks.crud.dao;

import ru.javarush.tasks.crud.model.entities.User;
import java.util.List;

/**
 * Created by eGarmin
 */
public interface UserDao {

    User findById(int id);
    void create(User user);
    void update(User user);
    void delete(User user);

    long totalCount();
    long totalCount(String name);

    List<User> findPage(int passRowsCount, int pageSize);
    List<User> findPage(String name, int passRowsCount, int pageSize);

}
