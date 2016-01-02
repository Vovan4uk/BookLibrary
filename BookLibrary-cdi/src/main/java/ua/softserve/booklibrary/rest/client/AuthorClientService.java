package ua.softserve.booklibrary.rest.client;

import ua.softserve.booklibrary.entity.Author;

import java.util.List;

public interface AuthorClientService {
	Author findAuthorByPk(Long id);

	List<Author> findAuthorsByRating(String rating);

	List<Author> findAllAuthors();

	Integer countAuthorsByRating(String rating);

	Integer countAuthorsWithoutRating();

	Integer countAllAuthors();

	void saveAuthor(Author author);

	void updateAuthor(Author author);

	void removeAuthor(Long id);
}
