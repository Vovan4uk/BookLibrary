package ua.softserve.booklibrary.service.serviceImpl;

import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.logging.Loggable;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@Loggable
public class ReviewServiceImpl implements ReviewService {
    @Inject
    private Event<Review> reviewAddedEvent;
    public static final Class REVIEW_CLASS = Review.class;
    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

    public Review findReviewById(Long id) {
        return (Review) em.find(REVIEW_CLASS, id);
    }

    @SuppressWarnings("unchecked")
    public List<Review> getAllReviews() {
        return em.createQuery("select r from Review r").getResultList();
    }

    public void removeReview(Review review) {
        em.remove(review);
    }

    public void saveReview(Review review) {
        Logger logger = Logger.getLogger("TestInterceptor");
        logger.info("++++++++++++++++++++++++++++++++++++Succsess+++++++++++++++++++++++++++++++++++");
        em.persist(review);
        reviewAddedEvent.fire(review);
    }
}
