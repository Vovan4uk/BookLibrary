package ua.softserve.booklibrary.dao.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReviewFacadeImpl extends GenericFacadeImpl<Review> implements ReviewFacade {
    public ReviewFacadeImpl() {
        super(Review.class);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewFacadeImpl.class);

    @Override
    public Integer countBookReviews(Book book) {
        LOGGER.debug("Get count reviews by book '{}'", book);
        return em.createNamedQuery("Review.countBookReviews", Number.class).setParameter("book", book).getSingleResult().intValue();
    }

    @Override
    public List<Review> findReviewsByAuthor(Author author) {
        LOGGER.debug("Find reviews by author '{}'", author);
        List<Review> reviews = em.createNamedQuery("Review.findReviewsByAuthor", Review.class).setParameter("author", author).getResultList();
        LOGGER.debug("Result list: {}", reviews);
        return reviews;
    }
}
