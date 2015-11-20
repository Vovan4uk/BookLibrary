package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookFacade extends GenericFacade<Book> {
    List<Book> findHotReleases();

    List<Book> findBooksByRating(Integer minRating);
}
