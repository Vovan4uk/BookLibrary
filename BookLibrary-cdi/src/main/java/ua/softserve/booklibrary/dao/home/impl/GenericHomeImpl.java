package ua.softserve.booklibrary.dao.home.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.home.GenericHome;
import ua.softserve.booklibrary.entity.LibraryEntity;
import ua.softserve.booklibrary.exception.AlreadyExistException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Set;

//todo: @TransactionAttribute ?
public abstract class GenericHomeImpl<T extends LibraryEntity> implements GenericHome<T> {

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
            em.persist(entity); // todo: NPE
            LOGGER.debug("Saved object: {}", entity);
        } catch (EntityExistsException e) { // todo: why catch only this exception?
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
            em.merge(entity); // todo: NPE (2 cases: entity and entity.id)
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
            em.remove(em.getReference(entityClass, id));    // todo: NPE?
            LOGGER.debug("Remove '{}' object with primary key '{}'", entityClass.getCanonicalName(), id);
        } catch (EntityNotFoundException e) {
            String errorMessage = "Remove unsuccessful. '"
                    + entityClass.getCanonicalName() + "' object with primary key '" + id + "' don't exist";
            LOGGER.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
    }

    @Override
    public void removeAll(Set<T> entities) {
        if (entities.isEmpty()) {
            String errorMessage = "List '" + entityClass.getCanonicalName() + "' is empty";
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        em.createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e IN (:entities)")
                .setParameter("entities", entities)
                .executeUpdate();
        em.flush();
        LOGGER.debug("Remove '{}' objects successful", entityClass.getCanonicalName());
    }
}
