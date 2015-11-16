package ua.softserve.booklibrary.dao.home.impl;

import ua.softserve.booklibrary.dao.home.ReviewHome;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class ReviewHomeImpl extends GenericHomeImpl<Review> implements ReviewHome {
    public ReviewHomeImpl() {
        super(Review.class);
    }

}
