package ua.softserve.booklibrary.rest.client.impl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.rest.client.AuthorServiceClient;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@Stateless
public class AuthorServiceClientImpl implements AuthorServiceClient {

    private String target = "http://localhost:8080/BookLibrary-cdi/rest/author";
    private Client client = ClientBuilder.newClient();

    @Override
    public Author findAuthorByPkThroughTheService(Long id) {
        Response response = client.target(target)
                .path("getauthor")
                .path(id.toString())
                .request()
                .get();
        return response.readEntity(Author.class);
    }

    @Override
    public void saveAuthorThroughTheService(Author author) {
        Response response = client.target(target)
                .path("saveauthor")
                .request()
                .post(Entity.entity(author, MediaType.APPLICATION_JSON));
        response.close();
    }
}
