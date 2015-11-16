package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class AuthorFacadeImpl extends GenericFacadeImpl<Author> implements AuthorFacade {
    public AuthorFacadeImpl() {
        super(Author.class);
    }
}
