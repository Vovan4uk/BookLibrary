package ua.softserve.booklibrary.dao.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.GenericFacade;
import ua.softserve.booklibrary.entity.LibraryEntity;
import ua.softserve.booklibrary.exception.LibraryException;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * GenericFacade implementation.
 *
 * @see ua.softserve.booklibrary.dao.facade.GenericFacade
 */
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
			String errorMessage = entityClass.getSimpleName() + " cannot be find by null primary key";
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
			LOGGER.error(errorMessage, e);
			throw new LibraryException(errorMessage, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		LOGGER.debug("Find all '{}' objects", entityClass.getCanonicalName());
		try {
			List<T> resultList = em.createQuery("select e from " + entityClass.getName() + " e")
					.getResultList();
			LOGGER.debug("Result list: {}", resultList);    // todo: LOGGER.trace - fixed
			return resultList;
		} catch (IllegalArgumentException e) {
			String errorMessage = "Find all '" + entityClass.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage, e);
			throw new LibraryException(errorMessage, e);
		}
	}

}
