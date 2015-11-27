package ua.softserve.booklibrary.bean;

import org.richfaces.JsfVersion;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AuthorAction implements Serializable {

    private static final long serialVersionUID = 3795838153393063077L;
    @EJB
    private AuthorManager authorManager;

    private List<Author> authors;
    private Author author;
    private Long currentAuthorIndex;
    private Author currentAuthor;

    public AuthorAction() {
    }

    public void save() {
        authorManager.save(author);
    }

    public void update() {
        authorManager.update(currentAuthor);
    }

    public void remove() {
        authorManager.removeByPk(currentAuthorIndex);
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
        return authorManager.findAuthorsByRating(minRating).size();
    }

    public Integer getCountAuthorsWithoutRating() {
        return authorManager.findAuthorsWithoutRating().size();
    }

    public void setCurrentAuthorIndex(Long currentAuthorIndex) {
        this.currentAuthorIndex = currentAuthorIndex;
    }

    public Long getCurrentAuthorIndex() {
        return currentAuthorIndex;
    }

    public void setCurrentAuthor(Author currentAuthor) {
        this.currentAuthor = currentAuthor;
    }

    public Author getCurrentAuthor() {
        return currentAuthor;
    }

    public void resetValues() {
        if (!JsfVersion.getCurrent().isCompliantWith(JsfVersion.JSF_2_2)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            UIComponent comp = fc.getViewRoot().findComponent("form:editGrid");

            ((EditableValueHolder) comp.findComponent("form:firstName")).resetValue();
            ((EditableValueHolder) comp.findComponent("form:secondName")).resetValue();
        }
    }
}
