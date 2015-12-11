package ua.softserve.booklibrary.bean.filter;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class BooksFilteringBean implements Serializable {
    private static final long serialVersionUID = -3725901188975943276L;
    private String authorFilter = "";
    @EJB
    private AuthorManager authorManager;

    public boolean getFilterAuthorImpl(Object current) {    // todo: refactoring!
        Book currentBook = (Book) current;
        if ("".equals(authorFilter)) {
            return true;
        }
        for (Author author : currentBook.getAuthors()) {
            if ((author.getFirstName() + " " + author.getSecondName()).toLowerCase().contains(authorFilter.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String getAuthorFilter() {
        return authorFilter;
    }

    public void setAuthorFilter(String authorFilter) {
        this.authorFilter = authorFilter;
    }
}