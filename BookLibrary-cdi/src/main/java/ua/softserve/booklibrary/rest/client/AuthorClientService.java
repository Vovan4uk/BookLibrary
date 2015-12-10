package ua.softserve.booklibrary.rest.client;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.AlreadyExistException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthorClientService {
    Author findAuthorByPk(Long id);

    List<Author> findAuthorsByRating(String rating);

    List<Author> findAllAuthors();

    void saveAuthor(Author author) throws AlreadyExistException;

    void updateAuthor(Author author) throws AlreadyExistException;

    void removeAuthor(Long id);
}
