package ua.softserve.booklibrary.dao.home.impl;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.home.GenericHome;
import ua.softserve.booklibrary.entity.LibraryEntity;
import ua.softserve.booklibrary.exception.LibraryException;

import javax.ejb.EJBException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;

/**
 * GenericHome implementation.
 *
 * @see ua.softserve.booklibrary.dao.home.GenericHome
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class GenericHomeImpl<T extends LibraryEntity> implements GenericHome<T> {

	@PersistenceContext(unitName = "OracleDS")
	protected EntityManager em;

	private final Class<T> entityClass;

	protected GenericHomeImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericHomeImpl.class);

	@Override
	public T save(T entity) {
		LOGGER.debug("Save '{}'", entityClass.getCanonicalName());
		if (entity == null) {
			String errorMessage = "Save unsuccessful. '" + entityClass.getCanonicalName() + "' is empty";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		try {
			em.persist(entity);
			LOGGER.debug("Saved object: {}", entity);
			return entity;
		} catch (EntityExistsException e) {
			String errorMessage = "Save unsuccessful. '" + entity + "' is already exist";
			LOGGER.error(errorMessage);     // todo: add variable e to logging (for all classes & places)
			throw new LibraryException(errorMessage);   // todo: lose stacktrace
		} catch (IllegalArgumentException e) {
			String errorMessage = "Save unsuccessful. '" + entity + "' is not valid";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		} catch (EJBException | PersistenceException | HibernateException e) {
			String errorMessage = "Save '" + entityClass.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
	}

	@Override
	public T update(T entity) {
		LOGGER.debug("Update '{}'", entityClass.getCanonicalName());
		if (entity == null) {
			String errorMessage = "Update unsuccessful. '" + entityClass.getCanonicalName() + "' is empty";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		try {
			em.merge(entity); // todo: entity.id == null ?
			LOGGER.debug("Updated: {}", entity);
			return entity;
		} catch (IllegalArgumentException e) {
			String errorMessage = "Update unsuccessful. '" + entity + "' do not exist";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		} catch (EJBException | PersistenceException | HibernateException e) {
			String errorMessage = "Update '" + entityClass.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
	}

	@Override
	public void removeByPk(Long id) {
		if (id == null) {
			String errorMessage = entityClass.getCanonicalName() + " cannot be remove by null primary key";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		try {
			em.remove(em.getReference(entityClass, id));
			LOGGER.debug("Remove '{}' with primary key '{}'", entityClass.getCanonicalName(), id);
		} catch (EntityNotFoundException e) {
			String errorMessage = "Remove unsuccessful. '" + entityClass.getCanonicalName() + "' with primary key '" + id + "' don't exist";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		} catch (IllegalArgumentException e) {
			String errorMessage = "Remove unsuccessful. '" + entityClass.getCanonicalName() + "' with PK '" + id + "' is already removed or detached";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		} catch (EJBException | HibernateException | PersistenceException e) {
			String errorMessage = "Remove '" + entityClass.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
	}

	@Override
	public void removeAll(Collection<T> entities) {
		if (entities.isEmpty()) {
			String errorMessage = "Collection '" + entityClass.getCanonicalName() + "' is empty";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		try {
			em.createQuery("DELETE FROM " + entityClass.getName() + " e WHERE e IN (:entities)")
					.setParameter("entities", entities)
					.executeUpdate();
			em.flush();
			LOGGER.debug("Remove '{}' successful", entityClass.getCanonicalName());
		} catch (EJBException | PersistenceException | HibernateException e) {
			String errorMessage = "Remove collection '" + entityClass.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
	}
}
