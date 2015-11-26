package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.dao.home.BookHome;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class BookManagerImpl implements BookManager {

    private static final Logger LOG = LoggerFactory.getLogger(BookManager.class);

    @EJB
    private BookHome bookHome;

    @EJB
    private BookFacade bookFacade;
    private String byRating;
    private String byAuthor;

    @Override
    public void save(Book entity) {
        LOG.debug("Save new Book ", entity);
        bookHome.save(entity);
    }

    @Override
    public Book update(Book entity) {
        return null;
    }

    @Override
    public void removeByPk(Long id) {

    }

    @Override
    public void removeAll(List<Book> entities) {

    }

    @Override
    public Book findByPk(Long id) {
        Book book = bookFacade.findByPk(id);
        book.getAuthors().size();
        book.getReviews().size();
        return book;
    }

    @Override
    public List<Book> findAll() {

        byRating = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byrating");
        byAuthor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byauthor");
        List<Book> resultList;
        if (byRating != null && !"".equals(byRating)) {
            resultList = getBooksByRating();
        } else if (byAuthor != null && !"".equals(byAuthor)) {
            resultList = getBooksByAuthor();
        } else {
            resultList = bookFacade.findAll();
        }
        return initBookList(resultList);

    }

    private List<Book> getBooksByRating() {
        try {
            Integer rating = Integer.parseInt(byRating);
            if (rating == 0) {
                return findBooksWithoutRating();
            } else if (rating > 0 && rating <= 5) {
                return findBooksByRating(rating);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return bookFacade.findAll();
        }
    }

    private List<Book> getBooksByAuthor() {
        try {
            Long authorId = Long.parseLong(byAuthor);
            if (authorId == 0) {
                throw new IllegalArgumentException();
            } else {
                return findBooksByAuthorId(authorId);
            }
        } catch (IllegalArgumentException e) {
            return bookFacade.findAll();
        }
    }

    private List<Book> initBookList(List<Book> books) {
        for (Book book : books) {
            book.getAuthors().size();
            book.getReviews().size();
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