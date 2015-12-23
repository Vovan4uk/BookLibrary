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

	@GET
	@Path("count/byRating/{rating}")
	Response countBooksByRating(@PathParam("rating") String rating);

	@GET
	@Path("count/withoutRating")
	Response countBooksWithoutRating();

}