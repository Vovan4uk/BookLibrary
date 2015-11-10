package ua.softserve.booklibrary.service.serviceImpl;

import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ReviewServiceImpl implements ReviewService {

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
        em.persist(review);
    }
}
