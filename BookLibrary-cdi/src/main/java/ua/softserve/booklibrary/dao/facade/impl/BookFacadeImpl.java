package ua.softserve.booklibrary.dao.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.exception.LibraryException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

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
		return em.createNamedQuery("Book.findHotReleases", Book.class).setMaxResults(5).getResultList();
	}

	@Override
	public List<Book> findBooksByRating(Integer minRating) {
		Integer maxRating = minRating + 1;
		return em.createNamedQuery("Book.findBooksByRating", Book.class).setParameter("minRating", minRating.doubleValue()).setParameter("maxRating", maxRating.doubleValue()).getResultList();
	}

	@Override
	public List<Book> findBooksWithoutRating() {
		return em.createNamedQuery("Book.findBooksWithoutRating", Book.class).getResultList();
	}

	@Override
	public List<Book> findLatestBooksByAuthorId(Long id, Integer count) {
		return em.createNamedQuery("Book.findLatestBooksByAuthorId", Book.class).setParameter("id", id).setMaxResults(count).getResultList();
	}

	@Override
	public List<Book> findBestBooksByAuthorId(Long id, Integer count) {
		return em.createNamedQuery("Book.findBestBooksByAuthorId", Book.class).setParameter("id", id).setMaxResults(count).getResultList();
	}

	@Override
	public List<Book> findBooksByAuthorId(Long id) {
		return em.createNamedQuery("Book.findBooksByAuthorId", Book.class).setParameter("id", id).getResultList();
	}

	@Override
	public List<Book> findBooksByAuthors(List<Author> authors) {
		List<Long> ids = new ArrayList<>();
		for (Author author : authors) {
			ids.add(author.getId());
		}
		return em.createNamedQuery("Book.findBooksByAuthors", Book.class).setParameter("ids", ids).getResultList();
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