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
public interface AuthorService {

    @GET
    @Path("get/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response getAuthor(@PathParam("id") Long id);

    @GET
    @Path("getbyrating/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Response getAuthorsByRating(@PathParam("id") String id);

    @GET
    @Path("getall")
    @Produces({MediaType.APPLICATION_JSON})
    Response getAllAuthors();

    @POST
    @Path("save")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    Response saveAuthor(Author author);

    @DELETE
    @Path("delete/{id}")
    Response removeAuthor(@PathParam("id") Long id);

    @PUT
    @Path("update/{id}")
    Response updateAuthor(@PathParam("id") Long id);

}