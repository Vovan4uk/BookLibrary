package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Local;

@Local
public interface ReviewFacade extends GenericFacade<Review> {
    Integer countBookReviews(Book book);
}
