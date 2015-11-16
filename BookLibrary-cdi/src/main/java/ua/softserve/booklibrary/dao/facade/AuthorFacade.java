package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Remote;

@Remote
public interface AuthorFacade extends GenericFacade<Author> {
}
