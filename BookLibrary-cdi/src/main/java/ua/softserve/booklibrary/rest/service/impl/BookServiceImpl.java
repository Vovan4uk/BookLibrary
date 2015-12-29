package ua.softserve.booklibrary.rest.service.impl;

import org.apache.commons.lang3.math.NumberUtils;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.BookManager;
import ua.softserve.booklibrary.rest.service.BookService;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.core.Response;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BookServiceImpl implements BookService {

	@EJB
	private BookManager bookManager;

	@Override
	public Response countBooksByRating(String rating) {
		try {
			Integer count = bookManager.countBooksByRating(rating);
			return Response.accepted(count).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response countBooksWithoutRating() {
		try {
			Integer count = bookManager.countBooksWithoutRating();
			return Response.accepted(count).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

}
