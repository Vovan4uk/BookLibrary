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

    @GET
    @Path("{id}")
    Response getAuthor(@PathParam("id") Long id);

    @GET
    @Path("byRating/{rating}")
    Response getAuthorsByRating(@PathParam("rating") String id);

    @GET
    @Path("all")
    Response getAllAuthors();

    @POST
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    Response saveAuthor(Author author);

    @PUT
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    Response updateAuthor(Author author);

    @DELETE
    @Path("{id}")
    Response removeAuthor(@PathParam("id") Long id);

}