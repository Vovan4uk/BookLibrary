package ua.softserve.booklibrary.dao.home.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.home.GenericHome;
import ua.softserve.booklibrary.entity.Entity;
import ua.softserve.booklibrary.exception.AlreadyExistException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
    public T save(T entity) throws AlreadyExistException {
        LOGGER.debug("Save '{}' object", entityClass.getCanonicalName());
        try {
            em.persist(entity);
            LOGGER.debug("Saved object: {}", entity);
        } catch (EntityExistsException e) {
            String errorMessage = "Save unsuccessful. '" + entity + "' is already exist";
            LOGGER.error(errorMessage);
            throw new AlreadyExistException(errorMessage);
        }
        return entity;
    }

    @Override
    public T update(T entity) {
        LOGGER.debug("Update '{}' object", entityClass.getCanonicalName());
        try {
            em.merge(entity);
            LOGGER.debug("Updated object: {}", entity);
        } catch (IllegalArgumentException e) {
            String errorMessage = "Update unsuccessful. '" + entity + "' do not exist";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
        return entity;
    }

    @Override
    public void removeByPk(Long id) {
        if (id == null) {
            String errorMessage = "Object cannot be remove by null primary key";
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        try {
            em.remove(em.getReference(entityClass, id));
            LOGGER.debug("Remove '{}' object with primary key '{}'", entityClass.getCanonicalName(), id);
        } catch (EntityNotFoundException e) {
            String errorMessage = "Remove unsuccessful. '" + entityClass.getCanonicalName() + "' object with primary key '" + id + "' don't exist";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
    }
}
