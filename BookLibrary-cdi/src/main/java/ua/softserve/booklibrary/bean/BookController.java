package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class BookController implements Serializable {

    private static final long serialVersionUID = 6489108625203081136L;

    @EJB
    private BookManager bookManager;

    private List<Book> hotReleases;
    private List<Book> books;
    private Book book;

    public void save() {
        bookManager.save(book);
    }

    public List<Book> getBooks() {
        if (books == null) {
            books = bookManager.findAll();
        }
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Book> getHotReleases() {
        if (hotReleases == null) {
            hotReleases = bookManager.findHotReleases();
        }
        return hotReleases;
    }

    private void initBooks() {
        String byauthor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byauthor");
    }

    public void setHotReleases(List<Book> hotReleases) {
        this.hotReleases = hotReleases;
    }

    public Integer getCountBooksByRating(Integer minRating) {
        return bookManager.findBooksByRating(minRating).size();
    }

    public Integer getCountBooksWithoutRating() {
        return bookManager.findBooksWithoutRating().size();
    }

}
