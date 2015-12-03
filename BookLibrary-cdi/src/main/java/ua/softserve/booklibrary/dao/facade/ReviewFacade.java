package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ReviewFacade extends GenericFacade<Review> {
    Integer countBookReviews(Book book);

    List<Review> findReviewsByAuthor(Author author);
}
