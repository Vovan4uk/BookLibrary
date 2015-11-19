package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.dao.home.ReviewHome;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.manager.ReviewManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class ReviewManagerImpl implements ReviewManager {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewManager.class);

    @EJB
    ReviewHome reviewHome;

    @EJB
    ReviewFacade reviewFacade;

    @Override
    public void save(Review entity) {
        LOG.debug("Save new Review ", entity);
        reviewHome.save(entity);
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
        return null;
    }

    @Override
    public List<Review> findAll() {
        return null;
    }

    @Override
    public Integer countBookReviews(Book book) {
        return reviewFacade.countBookReviews(book);
    }
}
