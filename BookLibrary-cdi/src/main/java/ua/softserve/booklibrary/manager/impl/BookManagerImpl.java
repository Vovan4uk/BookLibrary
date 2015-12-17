package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.dao.home.BookHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;

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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Book save(Book book) {
		if (bookFacade.isBookExist(book)) {
			String errorMessage = "Save unsuccessful. Book '" + book + "' is already exist or ISBN doesn't unique";
			LOGGER.error(errorMessage);
			throw new LibraryException(errorMessage);
		}
		LOGGER.debug("Save new Book {}", book);
		getReferenceForAuthors(book);
		return bookHome.save(book);
	}

	private void getReferenceForAuthors(Book book) {
		Set<Author> authors = new HashSet<>();
		List<Author> oldAuthors = new ArrayList<>(book.getAuthors());
		for (Author author : oldAuthors) {
			authors.add(authorFacade.findByPk(author.getId()));
		}
		book.setAuthors(authors);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeByPk(Long id) {   //todo: - fixed
		LOGGER.debug("Remove Book by primary key: {}", id);
		bookHome.removeByPk(id);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeAll(List<Book> books) {    //todo: ? - fixed
		LOGGER.debug("Remove list Books: {}", books);
		bookHome.removeAll(books);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Book findByPk(Long id) { //todo: really need to init all Authors and Reviews? - fixed (we use this object only in 'Book Details Page', so we need to init all data)
		Book book = bookFacade.findByPk(id);
		book.getAuthors().size();
		book.getReviews().size();
		return book;
	}

	@Override
	public List<Book> findAll() {
		String byRating = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byrating");
		List<Book> resultList;
		if (byRating != null && !byRating.isEmpty()) {
			resultList = getBooksByRating();
		} else {
			resultList = bookFacade.findAll();
		}
		return initBookList(resultList);
	}

	@Override
	public List<Book> findAll(String byRating) {
		List<Book> resultList;
		if (byRating != null && !byRating.isEmpty()) {
			resultList = getBooksByRating();
		} else {
			resultList = bookFacade.findAll();
		}
		return initBookList(resultList);
	}


	private List<Book> getBooksByRating() {
		String byRating = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byrating");
		List<Book> resultList;
		try {
			Integer rating = Integer.parseInt(byRating);
			if (rating == 0) {
				resultList = findBooksWithoutRating();
			} else if (rating > 0 && rating <= 5) {
				resultList = findBooksByRating(rating);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (IllegalFormatException e) {
			resultList = bookFacade.findAll();
		}
		return resultList;
	}

	private List<Book> initBookList(List<Book> books) { //todo: really need to init all Authors and Reviews? - fixed (delete init reviews, but not authors. we use author list(id, first name, second name) in all pages. and we need init author list for 'filtering by Author' in Book List Page)
		for (Book book : books) {
			book.getAuthors().size();
		}
		return books;
	}

	@Override
	public List<Book> findHotReleases() {
		return initBookList(bookFacade.findHotReleases());
	}

	@Override
	public List<Book> findBooksByRating(Integer minRating) {
		return initBookList(bookFacade.findBooksByRating(minRating));
	}

	@Override
	public List<Book> findBooksWithoutRating() {
		return initBookList(bookFacade.findBooksWithoutRating());
	}

	@Override
	public List<Book> findLatestBooksByAuthorId(Long id, Integer count) {
		return bookFacade.findLatestBooksByAuthorId(id, count);
	}

	@Override
	public List<Book> findBestBooksByAuthorId(Long id, Integer count) {
		return bookFacade.findBestBooksByAuthorId(id, count);
	}

	@Override
	public List<Book> findBooksByAuthorId(Long id) {
		return initBookList(bookFacade.findBooksByAuthorId(id));
	}

}