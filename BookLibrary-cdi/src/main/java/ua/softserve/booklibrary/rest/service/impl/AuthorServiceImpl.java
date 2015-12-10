package ua.softserve.booklibrary.rest.service.impl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.AlreadyExistException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.service.AuthorService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Stateless
public class AuthorServiceImpl implements AuthorService {

    @Context
    private UriInfo uriInfo;

    @EJB
    private AuthorManager authorManager;

    @Override
    public void saveNewAuthor(Author author) {
        try {
            authorManager.save(author);
        } catch (AlreadyExistException e) {
            System.err.println("------------Exception Service Level------------");
        }
    }

    @Override
    public Author getCustomerInfo(Long customerId) {
        return authorManager.findByPk(customerId);
    }
}
