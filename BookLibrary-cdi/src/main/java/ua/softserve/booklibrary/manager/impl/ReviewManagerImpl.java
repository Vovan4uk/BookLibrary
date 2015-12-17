package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.dao.home.ReviewHome;
import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.manager.ReviewManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class ReviewManagerImpl implements ReviewManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReviewManagerImpl.class);

	@EJB
	private ReviewHome reviewHome;
	@EJB
	private ReviewFacade reviewFacade;

	@Override
	public Review save(Review review) {
		LOGGER.debug("Save new Review {}", review);
		return reviewHome.save(review);
	}

	@Override
	public Review update(Review review) {
		LOGGER.debug("Update new Review {}", review);
		return reviewHome.update(review);
	}

	@Override
	public void removeByPk(Long id) {
		LOGGER.debug("Remove Review by primary key: {}", id);
		reviewHome.removeByPk(id);
	}

	@Override
	public void removeAll(List<Review> reviews) {
		LOGGER.debug("Remove list Reviews: {}", reviews);
		reviewHome.removeAll(reviews);
	}

	@Override
	public Review findByPk(Long id) {
		LOGGER.debug("Find Review by id: {}", id);
		return reviewFacade.findByPk(id);
	}

	@Override
	public List<Review> findAll() {
		LOGGER.debug("Find all Reviews");
		return reviewFacade.findAll();
	}
}