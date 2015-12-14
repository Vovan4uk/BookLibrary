package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AuthorManager extends GenericManager<Author> {
	List<Author> findAuthorsByRating(Integer minRating);

	List<Author> findAuthorsWithoutRating();

	Integer countAuthorsByRating(Integer minRating);

	Integer countAuthorsWithoutRating();

	List<Author> findAll(String byRating);
}
