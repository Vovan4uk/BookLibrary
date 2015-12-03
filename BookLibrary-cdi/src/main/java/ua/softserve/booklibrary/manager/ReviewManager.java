package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ReviewManager extends GenericManager<Review> {
    Integer countBookReviews(Book book);

    List<Review> findReviewsByAuthor(Author author);
}
