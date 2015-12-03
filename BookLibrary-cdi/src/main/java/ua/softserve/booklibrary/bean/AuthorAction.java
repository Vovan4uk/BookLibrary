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
import java.util.List;

@Named
@ViewScoped
public class AuthorAction implements Serializable {

    private static final long serialVersionUID = 3795838153393063077L;
    @EJB
    private AuthorManager authorManager;
    @EJB
    private ReviewManager reviewManager;

    private List<Author> authors;
    private Author newAuthor = new Author();
    private Author currentAuthor;

    private Long currentAuthorIndex;
    private String byRating;
    private String title;
    private String id;

    public void loadData() {
        try {
            currentAuthor = authorManager.findByPk(Long.parseLong(getId()));
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author id is not valid", e.getMessage()));
            currentAuthor = null;
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            currentAuthor = null;
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error has occurred. Try again, please.", e.getMessage()));
            currentAuthor = null;
        }
    }

    public void save() {
        try {
            authorManager.save(newAuthor);
            newAuthor = new Author();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Author saved successful", "Author saved successful"));
        } catch (AlreadyExistException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save unsuccessful", e.getMessage()));
        }
        initAuthors();
    }

    public void update() {
        try {
            authorManager.update(currentAuthor);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Author updated successful", "Author updated successful"));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update unsuccessful", e.getMessage()));
        }
        initAuthors();
    }

    public void remove() {
        try {
            authorManager.removeByPk(currentAuthorIndex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Author removed successful", "Author removed successful"));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remove unsuccessful", e.getMessage()));
        }
        initAuthors();
    }

    public Integer getCountAuthorsByRating(Integer minRating) {
        return authorManager.findAuthorsByRating(minRating).size();
    }

    public Integer getCountAuthorsWithoutRating() {
        return authorManager.findAuthorsWithoutRating().size();
    }

    public List<Review> getReviewsByAuthor(Author author) {
        return reviewManager.findReviewsByAuthor(author);
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

    public String getId() {
        if (id == null) {
            id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Author> getAuthors() {
        if (authors == null) {
            initAuthors();
        }
        return authors;
    }

    private void initAuthors() {
        authors = authorManager.findAll(getByRating());
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
