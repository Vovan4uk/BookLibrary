package ua.softserve.booklibrary.dao.facade;

import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Local;

@Local
public interface BookFacade extends GenericFacade<Book> {
}
