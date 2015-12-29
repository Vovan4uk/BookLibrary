package ua.softserve.booklibrary.rest.service;

import ua.softserve.booklibrary.entity.Author;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/author")
@Produces({MediaType.APPLICATION_JSON}) // todo: move to class level - fixed
@Consumes({MediaType.APPLICATION_JSON}) // todo: move to class level - fixed
public interface AuthorService {

	/**
	 * Find author by id in param request
	 *
	 * @param id - author id
	 * @return Author - author entity
	 */
	@GET
	@Path("{id}")
	Response getAuthor(@PathParam("id") Long id);

	/**
	 * Find authors by average rating in param request
	 *
	 * @param rating - average rating
	 * @return List<Author> - list of authors
	 */
	@GET
	@Path("byRating/{rating}")
	Response getAuthorsByRating(@PathParam("rating") String rating);

	/**
	 * Count of authors by average rating in param request
	 *
	 * @param rating - average rating
	 * @return Integer - count of authors
	 */
	@GET
	@Path("count/byRating/{rating}")
	Response countAuthorsByRating(@PathParam("rating") String rating);

	/**
	 * Count of authors without average rating
	 *
	 * @return Integer - count of authors
	 */
	@GET
	@Path("count/withoutRating")
	Response countAuthorsWithoutRating();

	/**
	 * Find all authors
	 *
	 * @return List<Author> - list of authors
	 */
	@GET
	@Path("all")
	Response getAllAuthors();

	/**
	 * Persist author in param request.
	 *
	 * @param author - author to save
	 * @return response status
	 */
	@POST
	@Produces({MediaType.APPLICATION_FORM_URLENCODED})
	Response saveAuthor(Author author);

	/**
	 * Update author in param request.
	 *
	 * @param author - author to update
	 * @return response status
	 */
	@PUT
	@Produces({MediaType.APPLICATION_FORM_URLENCODED})
	Response updateAuthor(Author author);

	/**
	 * Delete author in param request.
	 *
	 * @param id - author id
	 * @return response status
	 */
	@DELETE
	@Path("{id}")
	Response removeAuthor(@PathParam("id") Long id);

}