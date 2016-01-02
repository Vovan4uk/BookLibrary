package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Review;

/**
 * Review Facade Local interface
 *
 * @see ua.softserve.booklibrary.dao.facade.impl.ReviewFacadeImpl
 */
public interface ReviewFacade extends GenericFacade<Review> {

	/**
	 * Get count of all Review entities.
	 *
	 * @return Integer - count of all entities
	 */
	Integer countAllReviews();

}
