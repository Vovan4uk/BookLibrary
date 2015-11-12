package ua.softserve.booklibrary.service;

import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.logging.Loggable;

import javax.ejb.Remote;
import java.util.List;

@Remote
@Loggable
public interface ReviewService {
    Review findReviewById(Long id);
    List getAllReviews();
    void removeReview(Review review);
    void saveReview(Review review);
}
