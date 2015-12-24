package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Author;

import java.util.List;

/**
 * Author Facade Local interface
 * Facade interface provide read operations for Author entity.
 *
 * @see ua.softserve.booklibrary.dao.facade.impl.AuthorFacadeImpl
 */
public interface AuthorFacade extends GenericFacade<Author> {

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
	Integer countAuthorsByRating(Integer minRating);

	/**
	 * Get count of Author entities without average rating.
	 *
	 * @return Integer - count of entities
	 */
	Integer countAuthorsWithoutRating();

	/**
	 * Find Author entity.
	 *
	 * @param author - Author entity.
	 * @return boolean - is Author already exist
	 */
	boolean isAuthorExist(Author author);

}