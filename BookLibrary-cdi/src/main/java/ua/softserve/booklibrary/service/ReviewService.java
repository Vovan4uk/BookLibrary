package ua.softserve.booklibrary.service;

import ua.softserve.booklibrary.entity.Review;
import javax.ejb.Remote;
import java.util.List;
@Remote
public interface ReviewService {
    Review findReviewById(Long id);
    List getAllReviews();
    void removePatient(Review review);
    void savePatient(Review review);
}
