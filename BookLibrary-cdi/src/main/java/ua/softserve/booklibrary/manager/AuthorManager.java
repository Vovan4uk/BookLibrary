package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Author;

import java.util.List;

/**
 * Author Manager Local interface
 *
 * @see ua.softserve.booklibrary.manager.GenericManager
 */
public interface AuthorManager extends GenericManager<Author> {

	/**
	 * Find Author entities which average rating between minRating and maxRating.
	 *
	 * @param minRating - minimal rating. Maximal rating = minRating + 1.
	 * @return List<Author> - list entities
	 */
	List<Author> findAuthorsByRating(Integer minRating);

	/**
	 * Find Author entities without average rating.
	 *
	 * @return List<Author> - list entities
	 */
	List<Author> findAuthorsWithoutRating();

	/**
	 * Get count of Author entities which average rating between minRating and maxRating.
	 *
	 * @param minRating - minimal rating. Maximal rating <= minRating + 1.
	 * @return Integer - count of entities
	 */
	Integer countAuthorsByRating(String minRating);

	/**
	 * Get count of Author entities without average rating.
	 *
	 * @return Integer - count of entities
	 */
	Integer countAuthorsWithoutRating();

	/**
	 * Get count of all Author entities.
	 *
	 * @return Integer - count of all entities
	 */
	Integer countAllAuthors();

	/**
	 * Find Author entities by average rating.
	 *
	 * @param byRating - average rating.
	 * @return List<Author> - list entities
	 */
	List<Author> findByRating(String byRating);
}