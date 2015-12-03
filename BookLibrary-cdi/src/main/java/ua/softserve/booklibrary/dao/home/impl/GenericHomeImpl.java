package ua.softserve.booklibrary.dao.home.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericHomeImpl.class);

    @Override
    public T save(T entity) {
        LOGGER.debug("Save '{}' object", entityClass.getCanonicalName());
        em.persist(entity);
        LOGGER.debug("Saved object: {}", entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        LOGGER.debug("Update '{}' object", entityClass.getCanonicalName());
        em.merge(entity);
        LOGGER.debug("Updated object: {}", entity);
        return entity;
    }

    @Override
    public void removeByPk(Long id) {
        if (id == null) {
            String errorMessage = "Object cannot be remove by null primary key";
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        LOGGER.debug("Remove '{}' object with primary key '{}'", entityClass.getCanonicalName(), id);
        Object existEntity = em.getReference(entityClass, id);
        em.remove(existEntity);
//        em.detach(existEntity);
//        em.flush();
    }
}
