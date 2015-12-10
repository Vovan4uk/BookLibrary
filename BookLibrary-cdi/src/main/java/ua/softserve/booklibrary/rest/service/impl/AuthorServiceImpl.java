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
import java.net.URI;

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
            System.err.println("------------Exception Service Level------------");
        }
        return null;
    }

    @Override
    public Author getCustomerInfo(Long customerId) {
        return authorManager.findByPk(customerId);
    }
}
