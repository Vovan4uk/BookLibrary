package ua.softserve.booklibrary.service.serviceImpl;

import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    public void removePatient(Review review) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(review);
        transaction.commit();
    }

    public void savePatient(Review review) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(review);
        transaction.commit();
    }
}
