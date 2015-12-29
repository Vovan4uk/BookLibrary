package ua.softserve.booklibrary.dao.home.impl;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author Home Bean
 *
 * @see ua.softserve.booklibrary.dao.home.AuthorHome
 */
@Named
@Stateless
public class AuthorHomeImpl extends GenericHomeImpl<Author> implements AuthorHome {
	@PersistenceContext(unitName = "OracleDS")
	protected EntityManager em;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorHomeImpl.class);

	public AuthorHomeImpl() {
		super(Author.class);
	}

	@Override
	public void removeByPk(Long id) {
		if (id == null) {
			String errorMessage = Author.class.getCanonicalName() + " cannot be remove by null primary key";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		try {
			Author author = em.getReference(Author.class, id);
			if (author.getCountBooks() != 0) {
				String errorMessage = "Remove unsuccessful. '" + Author.class.getCanonicalName() + "' with primary key '" + id + "' has at least one book";
				LOGGER.error(errorMessage);
				throw new LibraryException(errorMessage);
			}
			em.remove(author);
			LOGGER.debug("Remove Author with primary key '{}'", id);
		} catch (EntityNotFoundException e) {
			String errorMessage = "Remove unsuccessful. '" + Author.class.getCanonicalName() + "' with primary key '" + id + "' don't exist";
			LOGGER.error(errorMessage, e);
			throw new LibraryException(errorMessage, e);
		} catch (IllegalArgumentException e) {
			String errorMessage = "Remove unsuccessful. '" + Author.class.getCanonicalName() + "' with PK '" + id + "' is already removed or detached";
			LOGGER.error(errorMessage, e);
			throw new LibraryException(errorMessage, e);
		} catch (EJBException | HibernateException | PersistenceException e) {
			String errorMessage = "Remove '" + Author.class.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage, e);
			throw new LibraryException(errorMessage, e);
		}
	}

	@Override
	public void removeAll(Collection<Author> authors) {
		List<Author> authorList = new ArrayList<>();
		for (Author author : authors) {
			if (author.getCountBooks() == 0) {
				authorList.add(author);
			}
		}
		if (authorList.isEmpty()) {
			String errorMessage = "Collection '" + Author.class.getCanonicalName() + "' is empty";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		try {
			em.createQuery("DELETE FROM " + Author.class.getName() + " a WHERE a IN (:authors)")
					.setParameter("authors", authorList)
					.executeUpdate();
			em.flush();
			LOGGER.debug("Remove '{}' successful", Author.class.getCanonicalName());
		} catch (EJBException | PersistenceException | HibernateException e) {
			String errorMessage = "Remove collection '" + Author.class.getCanonicalName() + "' unsuccessful. " + e.getMessage();
			LOGGER.error(errorMessage, e);
			throw new LibraryException(errorMessage, e);
		}
	}
}
