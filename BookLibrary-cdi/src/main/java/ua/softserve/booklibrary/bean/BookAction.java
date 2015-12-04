package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.exception.AlreadyExistException;
import ua.softserve.booklibrary.manager.BookManager;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class BookAction implements Serializable {

    private static final long serialVersionUID = 6489108625203081136L;

    @EJB
    private BookManager bookManager;

    private List<Book> hotReleases;
    private List<Book> books;
    private Book book;
    private Book currentBook;
    private String bookId;

    public void save() {
        try {
            bookManager.save(book);
        } catch (AlreadyExistException e) {

        }
    }

    public Book getCurrentBook() {
        if (currentBook == null) {
            bookId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
            try {
                currentBook = bookManager.findByPk(Long.parseLong(bookId));
            } catch (IllegalArgumentException e) {
                currentBook = null;
            }
        }
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
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
        return initBooks(hotReleases);
    }

    private List<Book> initBooks(List<Book> books) {
        for (Book book : books) {
            book.getReviews().size();
        }
        return books;
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

    public List<Book> getLatestBooksByAuthorId(Long id, Integer count) {
        return bookManager.findLatestBooksByAuthorId(id, count);
    }

    public List<Book> getBestBooksByAuthorId(Long id, Integer count) {
        return bookManager.findBestBooksByAuthorId(id, count);
    }

}