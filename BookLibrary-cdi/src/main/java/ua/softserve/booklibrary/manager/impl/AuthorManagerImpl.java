package ua.softserve.booklibrary.manager.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.dao.home.BookHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

/**
 * Author Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see ua.softserve.booklibrary.manager.AuthorManager
 */
@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AuthorManagerImpl implements AuthorManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorManager.class);

	@EJB
	private AuthorHome authorHome;
	@EJB
	private AuthorFacade authorFacade;
	@EJB
	private BookHome bookHome;
	@EJB
	private BookFacade bookFacade;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author save(Author author) {
		if (authorFacade.isAuthorExist(author)) {
			String errorMessage = "Save unsuccessful. Author '" + author + "' is already exist";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		LOGGER.debug("Save new Author {}", author);
		return authorHome.save(author);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author update(Author author) {
		if (authorFacade.isAuthorExist(author)) {
			String errorMessage = "Update unsuccessful. Author '" + author + "' is already exist";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		LOGGER.debug("Update Author {}", author);
		return authorHome.update(author);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeByPk(Long id) {
		LOGGER.debug("Remove Author by primary key: {}", id);
		authorHome.removeByPk(id);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeAll(List<Author> authors) {
		LOGGER.debug("Remove list Authors: {}", authors);
		authorHome.removeAll(authors);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Author findByPk(Long id) {
		LOGGER.debug("Find author by primary key: {}", id);
		Author author = authorFacade.findByPk(id);
		author.getBooks().size();   //todo: move this code to findByPk() - fixed
		return author;
	}

	@Override
	public List<Author> findAll() {
		LOGGER.debug("Find all authors");
		return authorFacade.findAll();
	}

	@Override
	public List<Author> findByRating(String byRating) { // todo: findByRating - fixed
		LOGGER.debug("Find authors by rating");
		return getAuthors(byRating);
	}

	private List<Author> getAuthors(String byRating) {
		List<Author> resultList;
		Integer rating = NumberUtils.toInt(byRating, 6);
		if (rating == 0) {
			resultList = findAuthorsWithoutRating();
		} else if (rating > 0 && rating <= 5) {
			resultList = findAuthorsByRating(rating);
		} else {
			resultList = findAll();
		}
		return resultList;
	}

	@Override
	public List<Author> findAuthorsByRating(Integer minRating) {
		LOGGER.debug("Find authors with rating {}", minRating);
		return authorFacade.findAuthorsByRating(minRating);
	}

	@Override
	public List<Author> findAuthorsWithoutRating() {
		LOGGER.debug("Find authors without rating");
		return authorFacade.findAuthorsWithoutRating();
	}

	@Override
	public Integer countAuthorsByRating(String minRating) {
		LOGGER.debug("Count authors with rating {}", minRating);
		return authorFacade.countAuthorsByRating(minRating);
	}

	@Override
	public Integer countAuthorsWithoutRating() {
		LOGGER.debug("Count authors without rating");
		return authorFacade.countAuthorsWithoutRating();
	}
}
