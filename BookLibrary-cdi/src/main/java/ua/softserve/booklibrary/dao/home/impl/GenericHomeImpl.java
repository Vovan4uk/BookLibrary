package ua.softserve.booklibrary.dao.home.impl;

import ua.softserve.booklibrary.dao.home.GenericHome;
import ua.softserve.booklibrary.entity.EntityInterface;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericHomeImpl<T extends EntityInterface> implements GenericHome<T> {

    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

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

        Object existEntity = em.getReference(entityClass, id);
        em.remove(existEntity);

//        em.remove(em.contains(entity) ? entity : em.merge(entity));
//        em.remove(entity);
    }
}
