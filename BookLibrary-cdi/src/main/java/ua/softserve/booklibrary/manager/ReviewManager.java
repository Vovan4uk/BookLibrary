package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Local;

@Local
public interface ReviewManager extends GenericManager<Review> {
    Integer countBookReviews(Book book);
}
