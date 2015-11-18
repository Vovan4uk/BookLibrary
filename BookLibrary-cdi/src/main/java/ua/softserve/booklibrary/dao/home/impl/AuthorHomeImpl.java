package ua.softserve.booklibrary.dao.home.impl;

import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class AuthorHomeImpl extends GenericHomeImpl<Author> implements AuthorHome {
    public AuthorHomeImpl() {
        super(Author.class);
    }

}
