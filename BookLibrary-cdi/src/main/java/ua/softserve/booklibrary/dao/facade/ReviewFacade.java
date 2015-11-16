package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Remote;

@Remote
public interface ReviewFacade extends GenericFacade<Review> {
}
