package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ManagedBean
@ViewScoped
public class AuthorController implements Serializable {

    private static final long serialVersionUID = 3795838153393063077L;
    @EJB
    private AuthorManager authorManager;

    private List<Author> authors;
    private Author author = new Author();

    public AuthorController() {
    }

    public void save() {
        authorManager.save(author);
    }

    public List<Author> getAuthors() {
        if (authors == null) {
            authors = authorManager.findAll();
        }
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
