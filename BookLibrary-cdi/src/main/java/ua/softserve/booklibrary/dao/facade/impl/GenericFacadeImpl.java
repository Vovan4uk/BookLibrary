package ua.softserve.booklibrary.dao.facade.impl;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.GenericFacade;
import ua.softserve.booklibrary.entity.LibraryEntity;
import ua.softserve.booklibrary.exception.LibraryException;

import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
			String errorMessage = entityClass.getSimpleName() + " cannot be find by null primary key"; // todo: add class name to message - fixed
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		LOGGER.debug("Find '{}' object with primary key '{}'", entityClass.getCanonicalName(), id);
		try {
			T entity = em.find(entityClass, id);
			if (entity == null) {
				String errorMessage = entityClass.getSimpleName() + " with primary key " + id + " doesn't exist.";
				LOGGER.error(errorMessage);
				throw new LibraryException(errorMessage);
			}
			LOGGER.debug("Result: {}", entity);
			return entity;
		} catch (IllegalArgumentException e) {
			String errorMessage = entityClass.getSimpleName() + " cannot be find by primary key" + e.getMessage();
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		LOGGER.debug("Find all '{}' objects", entityClass.getCanonicalName());
		try {
			List<T> resultList = em.createQuery("select e from " + entityClass.getName() + " e").getResultList();
			LOGGER.debug("Result list: {}", resultList);
			return resultList;
		} catch (IllegalArgumentException e) {
			String errorMessage = "Find all '" + entityClass.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
	}

}
