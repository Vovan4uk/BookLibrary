package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Review;

/**
 * Review Manager Local interface
 *
 * @see ua.softserve.booklibrary.manager.GenericManager
 */
public interface ReviewManager extends GenericManager<Review> {

	/**
	 * Get count of all Review entities.
	 *
	 * @return Integer - count of all entities
	 */
	Integer countAllReviews();
}