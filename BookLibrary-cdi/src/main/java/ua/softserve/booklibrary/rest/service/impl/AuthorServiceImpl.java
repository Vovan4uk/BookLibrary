package ua.softserve.booklibrary.rest.service.impl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.service.AuthorService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
public class AuthorServiceImpl implements AuthorService {

	@Context
	private UriInfo uriInfo;

	@EJB
	private AuthorManager authorManager;

	@Override
	public Response getAuthor(Long id) {
		Author author = authorManager.findByPk(id);
		return Response.accepted(author).build();
	}

	@Override
	public Response getAuthorsByRating(String rating) {
		List<Author> authors = authorManager.findAll(rating);
		for (Author author : authors) {
			author.setBooks(null);
		}
		return Response.accepted(authors).build();
	}

	@Override
	public Response getAllAuthors() {
		List<Author> authors = authorManager.findAll();
		for (Author author : authors) {
			author.setBooks(null);
		}
		return Response.accepted(authors).build();
	}

	@Override
	public Response saveAuthor(Author author) {
		try {
			authorManager.save(author);
			return Response.status(200).build();
		} catch (LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response updateAuthor(Author author) {
		try {
			authorManager.update(author);
			return Response.status(200).build();
		} catch (LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response removeAuthor(Long id) {
		authorManager.removeByPk(id);
		return Response.status(200).build();
	}
}
