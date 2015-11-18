package ua.softserve.booklibrary.dao.home;

import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Local;

@Local
public interface BookHome extends GenericHome<Book> {
}
