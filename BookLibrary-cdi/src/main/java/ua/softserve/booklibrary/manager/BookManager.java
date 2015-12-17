package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Book;

import java.util.List;

public interface BookManager extends GenericManager<Book> {
	List<Book> findHotReleases();

	List<Book> findBooksByRating(Integer minRating);

	List<Book> findBooksWithoutRating();

	List<Book> findLatestBooksByAuthorId(Long id, Integer count);

	List<Book> findBestBooksByAuthorId(Long id, Integer count);

	List<Book> findBooksByAuthorId(Long id);

	List<Book> findAll(String byRating);

}
