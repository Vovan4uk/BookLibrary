package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.ReviewFacade;
import ua.softserve.booklibrary.entity.Review;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class ReviewFacadeImpl extends GenericFacadeImpl<Review> implements ReviewFacade {
}
