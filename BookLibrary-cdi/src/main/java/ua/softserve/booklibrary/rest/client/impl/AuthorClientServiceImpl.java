package ua.softserve.booklibrary.rest.client.impl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.AlreadyExistException;
import ua.softserve.booklibrary.rest.client.AuthorClientService;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@Stateless
public class AuthorClientServiceImpl implements AuthorClientService {

    private String target = "http://localhost:8080/BookLibrary-cdi/rest/author";    // todo: final
    private Client client = ClientBuilder.newClient();  // todo: final

    @Override
    public Author findAuthorByPk(Long id) {
        Response response = client.target(target)
                .path("get")
                .path(id.toString())
                .request()
                .get();
        return response.readEntity(Author.class);
    }

    @Override
    public List<Author> findAuthorsByRating(String rating) {
        Response response = client.target(target)
                .path("getbyrating")
                .path(rating)
                .request()
                .get();
        return response.readEntity((new GenericType<List<Author>>() {
        }));
    }

    @Override
    public List<Author> findAllAuthors() {
        Response response = client.target(target)
                .path("getall")
                .request()
                .get();
        return response.readEntity((new GenericType<List<Author>>() {
        }));
    }

    @Override
    public void saveAuthor(Author author) throws AlreadyExistException {
        Response response = client.target(target)
                .path("save")
                .request()
                .post(Entity.entity(author, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 422) {
            throw new AlreadyExistException(response.readEntity(String.class));
        }
        response.close();
    }

    @Override

    public void updateAuthor(Author author) throws AlreadyExistException {
        Response response = client.target(target)
                .path("update")
                .request()
                .put(Entity.entity(author, MediaType.APPLICATION_JSON));
        if (response.getStatus() == 422) {
            throw new AlreadyExistException(response.readEntity(String.class));
        }
        response.close();
    }

    @Override
    public void removeAuthor(Long id) {
        Response response = client.target(target)
                .path("delete")
                .path(id.toString())
                .request()
                .delete();
        response.close();
    }
}
