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
import javax.persistence.EntityManagerFactory;
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
    private EntityManagerFactory entityManagerFactory; // для JPA Criteria используем чисто JPA-ный EntityManagerFactory
    @Autowired
    private SessionFactory sessionFactory; // Для всего остального используем hibernate-прослойку SessionFactory

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
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class); // будем выбирать объект User
        Root<User> userRoot = criteriaQuery.from(User.class);                        // из таблицы User
        criteriaQuery.select(userRoot);                                              // будем выбирать объект User
        criteriaQuery.where(criteriaBuilder.equal(userRoot.get("id"), id));          // задаем критерий выборки

        EntityManager entityManager = entityManagerFactory.createEntityManager();
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
        // Т.к. теперь sessionFactory настроен на работу через entityManagerFactory, то просто
        // сделать getSession().delete(user) или getSession().remove(user) недостаточно из-за
        // того, что в entityManagerFactory перед удалением объект должен быть прикреплен.
        // Поэтому делаем так как ниже или аналогично, но методом remove:
        getSession().delete(getSession().contains(user) ? user : getSession().merge(user));
    }

    @Override
    public long totalCount() {
        // uniqueResult берет не первый элемент выборки, а единственный, если тот один
        // Если их будет много, то хрен знает, что произойдет
        return ((BigInteger) getSession().createNativeQuery("select count(*) from test.user").uniqueResult()).longValue();
    }

}