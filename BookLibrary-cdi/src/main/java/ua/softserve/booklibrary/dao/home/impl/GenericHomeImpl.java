package ua.softserve.booklibrary.dao.home.impl;

import ua.softserve.booklibrary.dao.home.GenericHome;
import ua.softserve.booklibrary.entity.Entity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericHomeImpl<T extends Entity> implements GenericHome<T> {

    @PersistenceContext(unitName = "OracleDS")
    protected EntityManager em;

    private final Class<T> entityClass;

    protected GenericHomeImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public T update(T entity) {
        return em.merge(entity);
    }

    @Override
    public void removeByPk(Long id) {
        if (id == null) {
            String errorMsg = "null argument";
            throw new IllegalArgumentException(errorMsg);
        }
        Object existEntity = em.getReference(entityClass, id);
        em.remove(existEntity);
    }
}
