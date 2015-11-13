package ua.softserve.booklibrary.service;

import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface AuthorService {
    Author findAuthorById(Long id);

    List getAllAuthors();

    void removeAuthor(Author author);

    void saveAuthor(Author author);
}
