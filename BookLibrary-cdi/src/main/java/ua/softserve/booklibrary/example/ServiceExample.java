package ua.softserve.booklibrary.example;

import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.service.ReviewService;

import javax.ejb.EJB;
import java.util.Date;

public class ServiceExample {

    @EJB
    ReviewService reviewService;

     public void setReview(){
        Review review = new Review();
        review.setCommenter_name("Vova");
        review.setComment_body("Good comment");
        review.setComment_rating(5);
        review.setComment_create(new Date());
        reviewService.savePatient(review);
    }
}
