package ua.softserve.booklibrary.dao.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;

/**
 * Review Facade Bean
 *
 * @see ua.softserve.booklibrary.dao.facade.ReviewFacade
 */
@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReviewFacadeImpl extends GenericFacadeImpl<Review> implements ReviewFacade {
	public ReviewFacadeImpl() {
		super(Review.class);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewFacadeImpl.class);
}
