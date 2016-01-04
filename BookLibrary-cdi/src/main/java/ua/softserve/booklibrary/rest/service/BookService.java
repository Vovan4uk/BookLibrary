package ua.softserve.booklibrary.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/book")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface BookService {

	/**
	 * Count of books by average rating in param request
	 *
	 * @param rating - average rating
	 * @return Integer - count of books
	 */
	@GET
	@Path("count/byRating/{rating}")
	Response countBooksByRating(@PathParam("rating") String rating);

	/**
	 * Count of books without average rating
	 *
	 * @return Integer - count of books
	 */
	@GET
	@Path("count/withoutRating")
	Response countBooksWithoutRating();

	/**
	 * Count of all books
	 *
	 * @return Integer - count of all books
	 */
	@GET
	@Path("count/allBooks")
	Response countAllBooks();

	/**
	 * Count of all reviews
	 *
	 * @return Integer - count of all reviews
	 */
	@GET
	@Path("count/allReviews")
	Response countAllReviews();

	/**
	 * Get most popular books
	 *
	 * @return List<Book> - list of books
	 */
	@GET
	@Path("mostPopular")
	Response getMostPopular();
}