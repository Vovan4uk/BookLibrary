package ua.softserve.booklibrary.rest.client.impl;

import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.rest.client.BookClientService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

@Named
@RequestScoped
public class BookClientServiceImpl implements BookClientService {

	private static final String TARGET = "http://localhost:8080/BookLibrary-cdi/rest/book";     // todo: static - fixed
	private static final Client CLIENT = ClientBuilder.newClient();     // todo: static - fixed

	@Override
	public Integer countBooksByRating(String rating) {
		Response response = CLIENT.target(TARGET)
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
	public Integer countBooksWithoutRating() {
		Response response = CLIENT.target(TARGET)
				.path("count/withoutRating")
				.request()
				.get();
		if (response.getStatus() == 422) {
			throw new LibraryException(response.readEntity(String.class));
		}
		return response.readEntity(Integer.class);
	}
}
