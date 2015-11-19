package ua.softserve.booklibrary.bean;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class AuthorController {

    @EJB
    AuthorManager authorManager;

    private List<Author> authors = null;

    public AuthorController() {
    }

    public List<Author> getAuthors() {
        if (authors == null) {
            initAuthors();
        }
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    private void initAuthors() {
        authors = authorManager.findAll();
    }

}
