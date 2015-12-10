package ua.softserve.booklibrary.rest.client;

import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Local;

@Local
public interface AuthorServiceClient {
    Author findAuthorByPkThroughTheService(Long id);

    void saveAuthorThroughTheService(Author author);
}
