package ru.javarush.tasks.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.javarush.tasks.crud.dao.UserDao;
import ru.javarush.tasks.crud.model.Page;
import ru.javarush.tasks.crud.model.entities.User;

import java.util.List;

/**
 * Created by eGarmin
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void createOrUpdate(User user) {
        // С формы нельзя будет заполнить id и createdDate, но хакер сможет подменить http запрос
        // и что-то нахимичить. Чтобы этого не произошло, перед созданием или обновлением записи
        // будем начисто заnullять эти поля
        if (user.getId() == null || findById(user.getId()) == null) {
            user.setId(null);
            userDao.create(user);
        } else {
            userDao.update(user);
        }
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(findById(id));
    }

    @Override
    public int pageCount() {
        return pageCount(null);
    }

    @Override
    public int pageCount(String name) {
        long totalCount = 0;
        if (name == null) {
            totalCount = userDao.totalCount();
        } else {
            totalCount = userDao.totalCount(name);
        }
        return (int) Math.round(Math.ceil((totalCount + 0.0) / Page.getPageSize()));
    }

    @Override
    public List<User> findPage(String name, int pageNumber) {
        int passRowsCount = (pageNumber - 1) * Page.getPageSize();
        if (name == null) {
            return userDao.findPage(passRowsCount, Page.getPageSize());
        }
        return userDao.findPage(name, passRowsCount, Page.getPageSize());
    }

    @Override
    public List<User> findPage(int pageNumber) {
        return findPage(null, pageNumber);
    }

}