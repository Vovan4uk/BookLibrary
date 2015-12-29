package ua.softserve.booklibrary.manager.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.dao.home.BookHome;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

/**
 * Book Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see ua.softserve.booklibrary.manager.BookManager
 */
@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BookManagerImpl implements BookManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookManager.class);

	@EJB
	private BookHome bookHome;
	@EJB
	private BookFacade bookFacade;
	@EJB
	private AuthorFacade authorFacade;

	@Override
	public Book save(Book book) {
		if (bookFacade.isBookExist(book)) {
			String errorMessage = "Save unsuccessful. Book '" + book + "' is already exist or ISBN doesn't unique";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		LOGGER.debug("Save new Book {}", book);
		return bookHome.save(book);
	}

	@Override
	public Book update(Book book) {
		if (bookFacade.isBookExist(book)) {
			String errorMessage = "Update unsuccessful. Book '" + book + "' is already exist or ISBN doesn't unique";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		LOGGER.debug("Update current Book {}", book);
		return bookHome.update(book);
	}

	@Override
	public void removeByPk(Long id) {
		LOGGER.debug("Remove Book by primary key: {}", id);
		bookHome.removeByPk(id);
	}

	@Override
	public void removeAll(List<Book> books) {
		LOGGER.debug("Remove list Books: {}", books);
		bookHome.removeAll(books);
	}

	@Override
	public Book findByPk(Long id) {
		LOGGER.debug("Find and initialize book by id {}", id);
		Book book = bookFacade.findByPk(id);
		book.getAuthors().size();
		book.getReviews().size();
		LOGGER.debug("Result: {}", book);
		return book;
	}

	@Override
	public List<Book> findAll() {
		LOGGER.debug("Find all books");
		return initAuthorList(bookFacade.findAll());
	}

	@Override
	public List<Book> findByRating(String byRating) {
		LOGGER.debug("Find books by rating");
		return initAuthorList(getBooks(byRating));
	}

	private List<Book> getBooks(String byRating) {
		List<Book> resultList;
		Integer rating = NumberUtils.toInt(byRating);
		if (rating == 0) {
			resultList = findBooksWithoutRating();
		} else if (rating > 0 && rating <= 5) {
			resultList = findBooksByRating(rating);
		} else {
			resultList = findAll();
		}
		return resultList;
	}

	private List<Book> initAuthorList(List<Book> books) {
		LOGGER.debug("Initialize list books {}", books);
		for (Book book : books) {
			book.getAuthors().size();
		}
		return books;
	}

	@Override
	public List<Book> findHotReleases() {
		LOGGER.debug("Find HotReleases");
		return initAuthorList(bookFacade.findHotReleases());
	}

	@Override
	public List<Book> findBooksByRating(Integer minRating) {
		LOGGER.debug("Find books with rating {}", minRating);
		return bookFacade.findBooksByRating(minRating);
	}

	@Override
	public List<Book> findBooksWithoutRating() {
		LOGGER.debug("Find books without rating");
		return bookFacade.findBooksWithoutRating();
	}

	@Override
	public Integer countBooksByRating(String minRating) {
		LOGGER.debug("Count authors with rating {}", minRating);
		return bookFacade.countBooksByRating(minRating);
	}

	@Override
	public Integer countBooksWithoutRating() {
		LOGGER.debug("Count authors without rating");
		return bookFacade.countBooksWithoutRating();
	}

	@Override
	public List<Book> findLatestBooksByAuthorId(Long id, Integer count) {
		LOGGER.debug("Find {} latest books  by author id {}", count, id);
		return bookFacade.findLatestBooksByAuthorId(id, count);
	}

	@Override
	public List<Book> findBestBooksByAuthorId(Long id, Integer count) {
		LOGGER.debug("Find {} best books  by author id {}", count, id);
		return bookFacade.findBestBooksByAuthorId(id, count);
	}

	@Override
	public List<Book> findBooksByAuthorId(Long id) {
		LOGGER.debug("Find books  by author id {}", id);
		return initAuthorList(bookFacade.findBooksByAuthorId(id));
	}
}