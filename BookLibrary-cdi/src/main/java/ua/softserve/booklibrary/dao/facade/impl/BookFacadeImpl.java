package ua.softserve.booklibrary.dao.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.exception.LibraryException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

/**
 * Book Facade Bean
 *
 * @see ua.softserve.booklibrary.dao.facade.BookFacade
 */
@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class BookFacadeImpl extends GenericFacadeImpl<Book> implements BookFacade {
	public BookFacadeImpl() {
		super(Book.class);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(Book.class);

	@Override
	public List<Book> findHotReleases() {
		LOGGER.debug("Find HotReleases");
		List<Book> hotReleases = em.createNamedQuery("Book.findHotReleases", Book.class).setMaxResults(5).getResultList();
		LOGGER.debug("Result: {}", hotReleases);
		return hotReleases;
	}

	@Override
	public List<Book> findBooksByRating(Integer minRating) {
		Integer maxRating = minRating + 1;
		LOGGER.debug("Find books by rating between ({} and {})", minRating, maxRating);
		List<Book> result = em.createNamedQuery("Book.findBooksByRating", Book.class).setParameter("minRating", minRating.doubleValue()).setParameter("maxRating", maxRating.doubleValue()).getResultList();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public List<Book> findBooksWithoutRating() {
		LOGGER.debug("Find books without rating");
		List<Book> result = em.createNamedQuery("Book.findBooksWithoutRating", Book.class).getResultList();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public Integer countBooksByRating(Integer minRating) {
		Integer maxRating = minRating + 1;
		LOGGER.debug("Count books by rating between ({} and {})", minRating, maxRating);
		Integer result = em.createNamedQuery("Book.countBooksByRating", Number.class).setParameter("minRating", minRating.doubleValue()).setParameter("maxRating", maxRating.doubleValue()).getSingleResult().intValue();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public Integer countBooksWithoutRating() {
		LOGGER.debug("Count books without rating");
		Integer result = em.createNamedQuery("Book.countBooksWithoutRating", Number.class).getSingleResult().intValue();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public List<Book> findLatestBooksByAuthorId(Long id, Integer count) {
		LOGGER.debug("Find {} latest books  by author id {}", count, id);
		List<Book> result = em.createNamedQuery("Book.findLatestBooksByAuthorId", Book.class).setParameter("id", id).setMaxResults(count).getResultList();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public List<Book> findBestBooksByAuthorId(Long id, Integer count) {
		LOGGER.debug("Find {} best books  by author id {}", count, id);
		List<Book> result = em.createNamedQuery("Book.findBestBooksByAuthorId", Book.class).setParameter("id", id).setMaxResults(count).getResultList();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public List<Book> findBooksByAuthorId(Long id) {
		LOGGER.debug("Find books  by author id {}", id);
		List<Book> result = em.createNamedQuery("Book.findBooksByAuthorId", Book.class).setParameter("id", id).getResultList();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public boolean isBookExist(Book book) {
		if (StringUtils.isEmpty(book.getIsbn())) {
			String message = "Book '" + book + "' ISBN is empty.";
			LOGGER.error(message);
			throw new LibraryException(message);
		}
		if (book.getId() != null) {
			return isCurrentBookExist(book);
		} else {
			return isNewBookExist(book);
		}
	}

	private boolean isNewBookExist(Book book) {
		LOGGER.debug("Find new book by 'ISBN' {}", book.getIsbn());
		return em.createNamedQuery("Book.isBookExistByIsbn", Boolean.class)
				.setParameter("isbn", book.getIsbn())
				.getSingleResult();
	}

	private boolean isCurrentBookExist(Book book) {
		LOGGER.debug("Find current book by 'ISBN' {} and 'id' {}", book.getIsbn(), book.getId());
		return em.createNamedQuery("Book.isBookExistByIsbnWithId", Boolean.class)
				.setParameter("isbn", book.getIsbn())
				.setParameter("id", book.getId())
				.getSingleResult();
	}

}