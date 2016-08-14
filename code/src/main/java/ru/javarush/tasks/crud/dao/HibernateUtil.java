package ru.javarush.tasks.crud.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by eGarmin
 */
public class HibernateUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("ru.javarush.entity.manager.factory");

    public static CriteriaBuilder getCriteriaBuilder() {
        return ENTITY_MANAGER_FACTORY.getCriteriaBuilder();
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

}
