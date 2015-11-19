package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class BookFacadeImpl extends GenericFacadeImpl<Book> implements BookFacade {
    public BookFacadeImpl() {
        super(Book.class);
    }

    @Override
    public List<Book> findHotReleases() {
        return em.createNamedQuery("Book.findHotReleases", Book.class).setMaxResults(2).getResultList();
    }
}