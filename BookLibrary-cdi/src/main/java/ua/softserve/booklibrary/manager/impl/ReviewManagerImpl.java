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
    public Review update(Review entity) {
        return null;
    }

    @Override
    public void removeByPk(Long id) {

    }

    @Override
    public void removeAll(List<Review> entities) {

    }

    @Override
    public Review findByPk(Long id) {
        return reviewFacade.findByPk(id);
    }

    @Override
    public List<Review> findAll() {
        return null;
    }
}
