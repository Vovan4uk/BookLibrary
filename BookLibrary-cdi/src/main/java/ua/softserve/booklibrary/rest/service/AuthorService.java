package ua.softserve.booklibrary.rest.service;

import ua.softserve.booklibrary.entity.Author;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/author")
public interface AuthorService {

    @POST
    @Path("addauthor")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_FORM_URLENCODED})
    void saveNewAuthor(Author author);

    @GET
    @Path("getauthor/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Author getCustomerInfo(@PathParam("id") Long customerId);

}