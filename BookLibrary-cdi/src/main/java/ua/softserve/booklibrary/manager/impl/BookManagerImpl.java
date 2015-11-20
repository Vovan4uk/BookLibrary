package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.dao.home.BookHome;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class BookManagerImpl implements BookManager {

    private static final Logger LOG = LoggerFactory.getLogger(BookManager.class);

    @EJB
    BookHome bookHome;

    @EJB
    BookFacade bookFacade;

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
        return null;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = bookFacade.findAll();
        for (Book book : books) {
            book.getAuthors().size();
            book.getReviews().size();
        }
        return books;
    }

    @Override
    public List<Book> findHotReleases() {
        List<Book> books = bookFacade.findHotReleases();
        for (Book book : books) {
            book.getAuthors().size();
        }
        return books;
    }

    @Override
    public List<Book> findBooksByRating(Integer minRating, Integer maxRating) {
        return bookFacade.findBooksByRating(minRating, maxRating);
    }
}
