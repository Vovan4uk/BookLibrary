package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Book;

import java.util.List;

/**
 * Book Manager Local interface
 *
 * @see ua.softserve.booklibrary.manager.GenericManager
 */
public interface BookManager extends GenericManager<Book> {

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
	 * Find Book entities by average rating.
	 *
	 * @param byRating - average rating.
	 * @return List<Book> - list entities
	 */
	List<Book> findByRating(String byRating);

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


}
