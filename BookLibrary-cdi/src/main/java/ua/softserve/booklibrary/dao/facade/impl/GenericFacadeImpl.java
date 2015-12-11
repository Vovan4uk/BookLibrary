package ua.softserve.booklibrary.dao.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.GenericFacade;
import ua.softserve.booklibrary.entity.LibraryEntity;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public abstract class GenericFacadeImpl<T extends LibraryEntity> implements GenericFacade<T> {

    @PersistenceContext(unitName = "OracleDS")
    protected EntityManager em;

    private final Class<T> entityClass;

    protected GenericFacadeImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericFacadeImpl.class);

    @Override
    public T findByPk(Long id) {
        if (id == null) {
            String errorMessage = "Object cannot be find by null primary key"; // todo: add class name to message
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        LOGGER.debug("Find '{}' object with primary key '{}'", entityClass.getCanonicalName(), id);
        T entity = em.find(entityClass, id);
        LOGGER.debug("Result: {}", entity);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        LOGGER.debug("Find all '{}' objects", entityClass.getCanonicalName());
        List<T> resultList = em.createQuery("select e from " + entityClass.getName() + " e").getResultList();
        LOGGER.debug("Result list: {}", resultList);
        return resultList;
    }

}
