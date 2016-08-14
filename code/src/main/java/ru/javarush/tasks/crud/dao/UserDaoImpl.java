package ru.javarush.tasks.crud.dao;

//import org.hibernate.Criteria;
//import org.hibernate.criterion.Restrictions;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.javarush.tasks.crud.model.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by eGarmin
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User findById(int id) {
//        Criteria criteria = getSession().createCriteria(User.class);
//        criteria.add(Restrictions.eq("id", id)); // первый параметр - это имя поля класса, а не колонки таблицы
//        // uniqueResult берет не первый элемент выборки, а единственный, если тот один
//        // Если их будет много, то хрен знает, что произойдет
//        return (User) criteria.uniqueResult();
        CriteriaBuilder criteriaBuilder = HibernateUtil.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class); // будем выбирать объект User
        Root<User> userRoot = criteriaQuery.from(User.class);                        // из таблицы User
        criteriaQuery.select(userRoot);                                              // будем выбирать объект User
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("id"), id));          // задаем критерий выборки

        EntityManager entityManager = HibernateUtil.getEntityManager();
        return entityManager.createQuery(criteriaQuery).getSingleResult();           // мы знаем, что результат уникален
    }

    @Override
    public List<User> findByName(String name, int passRowsCount, int pageSize) {
        Query<User> query = getSession().createQuery("select u from User u where u.name = :name", User.class);
        query.setParameter("name", name);
        query.setFirstResult(passRowsCount);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<User> findPage(int passRowsCount, int pageSize) {
        // Теперь не SQL, а Native
        Query<User> query = getSession().createNativeQuery("select * from test.user limit :passRowsCount, :pageSize", User.class);
        query.setParameter("passRowsCount", passRowsCount);
        query.setParameter("pageSize", pageSize);
        return query.list();
    }

    @Override
    public void create(User user) {
        getSession().persist(user);
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public void delete(User user) {
        getSession().delete(user);
    }

    @Override
    public long totalCount() {
        // uniqueResult берет не первый элемент выборки, а единственный, если тот один
        // Если их будет много, то хрен знает, что произойдет
        return ((BigInteger) getSession().createNativeQuery("select count(*) from test.user").uniqueResult()).longValue();
    }

}