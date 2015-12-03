package ua.softserve.booklibrary.bean;

import org.richfaces.JsfVersion;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.exception.AlreadyExistException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.manager.ReviewManager;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class AuthorAction implements Serializable {

    private static final long serialVersionUID = 3795838153393063077L;
    @EJB
    private AuthorManager authorManager;
    @EJB
    private ReviewManager reviewManager;

    private List<Author> authors;
    private Author author;
    private String id;
    private Author newAuthor = new Author();
    private Long currentAuthorIndex;
    private Author currentAuthor;
    private String byRating;
    private String title;
    private Map<Object, Object> checkedItems = new HashMap<>();


    public AuthorAction() {
    }

    public Author getAuthor() {
        try {
            author = authorManager.findByPk(Long.parseLong(getId()));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author id is not valid", e.getMessage()));
            author = null;
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            author = null;
        }
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getId() {
        if (id == null) {
            id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Review> getReviewsByAuthor(Author author) {
        return reviewManager.findReviewsByAuthor(author);
    }


    public Map<Object, Object> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(Map<Object, Object> checkedItems) {
        this.checkedItems = checkedItems;
    }

    public String getTitle() {
        if (title == null) {
            try {
                Integer rating = Integer.parseInt(FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .getRequestParameterMap()
                        .get("byrating"));
                if (rating >= 0 && rating <= 5) {
                    title = "by rating " + rating;
                }
            } catch (NumberFormatException e) {
                //Maybe some error message
            }
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getByRating() {
        if (byRating == null) {
            byRating = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byrating");
        }
        return byRating;
    }

    public void setByRating(String byRating) {
        this.byRating = byRating;
    }

    public void save() {
        try {
            authorManager.save(newAuthor);
            newAuthor = new Author();
        } catch (AlreadyExistException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save unsuccessful", e.getMessage()));
        }
    }

    public void update() {
        try {
            authorManager.update(currentAuthor);
        } catch (AlreadyExistException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update unsuccessful", e.getMessage()));
        }
    }

    public void remove() {
        authorManager.removeByPk(currentAuthorIndex);
    }

    public List<Author> getAuthors() {
        authors = authorManager.findAll(getByRating());
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Author getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(Author newAuthor) {
        this.newAuthor = newAuthor;
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
