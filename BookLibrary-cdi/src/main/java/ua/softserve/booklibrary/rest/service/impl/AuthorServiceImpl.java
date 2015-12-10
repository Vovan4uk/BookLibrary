package ua.softserve.booklibrary.rest.service.impl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.AlreadyExistException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.service.AuthorService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
public class AuthorServiceImpl implements AuthorService {

    @Context
    private UriInfo uriInfo;

    @EJB
    private AuthorManager authorManager;

    @Override
    public Response saveAuthor(Author author) {
        try {
            authorManager.save(author);
            return Response.status(200).build();
        } catch (AlreadyExistException e) {
            return Response.status(422).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAuthor(Long id) {
        Author author = authorManager.findByPk(id);
        return Response.accepted(author).build();
    }

    @Override
    public Response getAuthorsByRating(String id) {
        List<Author> authors = authorManager.findAll(id);
        return Response.accepted(authors).build();
    }

    @Override
    public Response getAllAuthors() {
        List<Author> authors = authorManager.findAll();
        return Response.accepted(authors).build();
    }

    @Override
    public Response removeAuthor(Long id) {
        return null;
    }

    @Override
    public Response updateAuthor(Long id) {
        return null;
    }
}
