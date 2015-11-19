package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Local;
import java.util.List;

@Local
public interface BookManager extends GenericManager<Book> {
    List<Book> findHotReleases();

    List<Book> findBooksByRating(Integer minRating, Integer maxRating);
}
