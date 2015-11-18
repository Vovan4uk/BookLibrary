package ua.softserve.booklibrary.manager;

import ua.softserve.booklibrary.entity.Book;

import javax.ejb.Local;

@Local
public interface BookManager extends GenericManager<Book> {

}
