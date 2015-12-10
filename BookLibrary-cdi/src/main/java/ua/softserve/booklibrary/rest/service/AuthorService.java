package ua.softserve.booklibrary.rest.service;

import ua.softserve.booklibrary.entity.Author;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/author")
public interface AuthorService {

    @POST
    @Path("saveauthor")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    Response saveAuthor(Author author);

    @GET
    @Path("getauthor/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    Author getCustomerInfo(@PathParam("id") Long customerId);

}