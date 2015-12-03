package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthorFacade extends GenericFacade<Author> {
    List<Author> findAuthorsByRating(Integer minRating);

    List<Author> findAuthorsWithoutRating();

    Author findBySecondAndFirstName(String secondName, String firstName);

    Author findByFirstName(String firstName);
}
