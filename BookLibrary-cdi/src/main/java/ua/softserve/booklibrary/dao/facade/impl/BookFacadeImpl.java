package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Named;

@Named
@Stateless
public class BookFacadeImpl extends GenericFacadeImpl<Book> implements BookFacade {
    public BookFacadeImpl() {
        super(Book.class);
    }
}
