package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookFacade extends GenericFacade<Book> {
    List<Book> findHotReleases();

    List<Book> findBooksByRating(Integer minRating);

    List<Book> findBooksWithoutRating();

    List<Book> findLatestBooksByAuthorId(Long id, Integer count);

    List<Book> findBestBooksByAuthorId(Long id, Integer count);

    List<Book> findBooksByAuthorId(Long id);
}
