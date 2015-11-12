package ua.softserve.booklibrary.observer;


import ua.softserve.booklibrary.entity.Review;

import javax.enterprise.event.Observes;

public class ReviewObserver {
    public void reviewUpdate(@Observes Review review) {
        System.out.println("Review was add/updated. We need update avarage rating for book and authors");
    }
}
