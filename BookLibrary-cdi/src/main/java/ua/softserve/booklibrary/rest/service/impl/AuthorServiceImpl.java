package ua.softserve.booklibrary.rest.service.impl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.service.AuthorService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class AuthorServiceImpl implements AuthorService {

	@Context
	private UriInfo uriInfo;

	@EJB
	private AuthorManager authorManager;

	@Resource
	SessionContext context;

	@Override
	public Response getAuthor(Long id) {
		try {
			Author author = authorManager.findByPk(id);
			return Response.accepted(author).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response getAuthorsByRating(String rating) {
		try {
			List<Author> authors = authorManager.findAll(rating);
			for (Author author : authors) {
				author.setBooks(null);
			}
			return Response.accepted(authors).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response getAllAuthors() {
		try {
			List<Author> authors = authorManager.findAll();
			for (Author author : authors) {
				author.setBooks(null);
			}
			return Response.accepted(authors).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response saveAuthor(Author author) {
		try {
			authorManager.save(author);
			return Response.status(200).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response updateAuthor(Author author) {
		try {
			authorManager.update(author);
			return Response.status(200).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response removeAuthor(Long id) {
		try {
			authorManager.removeByPk(id);
			return Response.status(200).build();
		} catch (EJBException | LibraryException e) {
			return Response.status(422).entity(e.getMessage()).build();
		}
	}
}
