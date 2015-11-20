package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.manager.BookManager;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ManagedBean
@ViewScoped
public class BookController implements Serializable {
    private static final long serialVersionUID = 6489108625203081136L;
    @EJB
    private BookManager bookManager;

    private List<Book> hotReleases;
    private List<Book> books;

    public List<Book> getBooks() {
        if (books == null) {
            initBooks();
        }
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getHotReleases() {
        if (hotReleases == null) {
            initHotReleases();
        }
        return hotReleases;
    }

    public void setHotReleases(List<Book> hotReleases) {
        this.hotReleases = hotReleases;
    }

    private void initBooks() {
        books = bookManager.findAll();
    }

    private void initHotReleases() {
        hotReleases = bookManager.findHotReleases();
    }

    public Integer getCountBooksByRating(Integer minRating) {
        return bookManager.findBooksByRating(minRating).size();
    }
}
