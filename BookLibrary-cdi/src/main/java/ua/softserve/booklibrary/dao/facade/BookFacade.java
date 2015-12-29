package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Book;

import java.util.List;

/**
 * Book Facade Local interface
 * Facade interface provide read operations for Book entity.
 *
 * @see ua.softserve.booklibrary.dao.facade.impl.BookFacadeImpl
 */
public interface BookFacade extends GenericFacade<Book> {

	/**
	 * Find latest Book entities in the database.
	 *
	 * @return List<Book> - list entities
	 */
	List<Book> findHotReleases();

	/**
	 * Find Book entities which average rating between minRating and maxRating.
	 *
	 * @param minRating - minimal rating. Maximal rating = minRating + 1.
	 * @return List<Book> - list entities
	 */
	List<Book> findBooksByRating(Integer minRating);

	/**
	 * Find Book entities without average rating.
	 *
	 * @return List<Book> - list entities
	 */
	List<Book> findBooksWithoutRating();

	/**
	 * Get count of Book entities which average rating between minRating and maxRating.
	 *
	 * @param minRating - minimal rating. Maximal rating <= minRating + 1.
	 * @return Integer - count of entities
	 */
	Integer countBooksByRating(String minRating);

	/**
	 * Get count of Book entities without average rating.
	 *
	 * @return Integer - count of entities
	 */
	Integer countBooksWithoutRating();

	/**
	 * Find latest Book entities by Author id.
	 *
	 * @param id    - Author id.
	 * @param count - count Book entities in result list.
	 * @return List<Book> - list entities
	 */
	List<Book> findLatestBooksByAuthorId(Long id, Integer count);

	/**
	 * Find Book entities with greatest average rating by Author id.
	 *
	 * @param id    - Author id.
	 * @param count - count Book entities in result list.
	 * @return List<Book> - list entities
	 */
	List<Book> findBestBooksByAuthorId(Long id, Integer count);

	/**
	 * Find Book entities by Author id.
	 *
	 * @param id - Author id.
	 * @return List<Book> - list entities
	 */
	List<Book> findBooksByAuthorId(Long id);

	/**
	 * Find Book entity.
	 *
	 * @param book - Book entity.
	 * @return boolean - is Book already exist
	 */
	boolean isBookExist(Book book);
}
