package ua.softserve.booklibrary.dao.home.impl;

import ua.softserve.booklibrary.dao.home.BookHome;
import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Named;

/**
 * Book Home Bean
 *
 * @see ua.softserve.booklibrary.dao.home.BookHome
 */
@Named
@Stateless
public class BookHomeImpl extends GenericHomeImpl<Book> implements BookHome {
	public BookHomeImpl() {
		super(Book.class);
	}

}
