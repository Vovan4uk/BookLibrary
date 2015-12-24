package ua.softserve.booklibrary.rest.client.impl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.rest.client.AuthorClientService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@RequestScoped
public class AuthorClientServiceImpl implements AuthorClientService {

	private final String target = "http://localhost:8080/BookLibrary-cdi/rest/author";    // todo: static
	private final Client client = ClientBuilder.newClient();  // todo: static

	@Override
	public Author findAuthorByPk(Long id) {
		Response response = client.target(target)
				.path(id.toString())
				.request()
				.get();
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		return response.readEntity(Author.class);
	}

	@Override
	public List<Author> findAuthorsByRating(String rating) {
		Response response = client.target(target)
				.path("byRating")
				.path(rating)
				.request()
				.get();
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		return response.readEntity((new GenericType<List<Author>>() {
		}));
	}

	@Override
	public Integer countAuthorsByRating(String rating) {
		Response response = client.target(target)
				.path("count/byRating")
				.path(rating)
				.request()
				.get();
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		return response.readEntity(Integer.class);
	}

	@Override
	public Integer countAuthorsWithoutRating() {
		Response response = client.target(target)
				.path("count/withoutRating")
				.request()
				.get();
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		return response.readEntity(Integer.class);
	}

	@Override
	public List<Author> findAllAuthors() {
		Response response = client.target(target)
				.path("all")
				.request()
				.get();
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		return response.readEntity((new GenericType<List<Author>>() {
		}));
	}

	@Override
	public void saveAuthor(Author author) {
		Response response = client.target(target)
				.request()
				.post(Entity.entity(author, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		response.close();
	}

	@Override
	public void updateAuthor(Author author) {
		Response response = client.target(target)
				.request()
				.put(Entity.entity(author, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		response.close();
	}

	@Override
	public void removeAuthor(Long id) {
		Response response = client.target(target)
				.path(id.toString())
				.request()
				.delete();
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		response.close();
	}
}
