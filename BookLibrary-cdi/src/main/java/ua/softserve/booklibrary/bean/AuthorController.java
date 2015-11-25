package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
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

    public void remove(Long id) {
        authorManager.removeByPk(id);
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

    public Integer getCountAuthorsByRating(Integer minRating) {
        if (minRating >= 0 && minRating <= 5) {
            return authorManager.findAuthorsByRating(minRating).size();
        }
        return 0;
    }

    public Integer getCountAuthorsWithoutRating() {
        return authorManager.findAuthorsWithoutRating().size();
    }

}
