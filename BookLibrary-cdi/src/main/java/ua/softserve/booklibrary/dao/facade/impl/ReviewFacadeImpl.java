package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@Stateless
public class ReviewFacadeImpl extends GenericFacadeImpl<Review> implements ReviewFacade {
    public ReviewFacadeImpl() {
        super(Review.class);
    }

    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

    @Override
    public Integer countBookReviews(Book book) {
        return em.createNamedQuery("Review.countBookReviews", Long.class).setParameter("book", book).getSingleResult().intValue();
    }
}
