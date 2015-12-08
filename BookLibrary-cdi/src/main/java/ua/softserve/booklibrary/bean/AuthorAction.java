package ua.softserve.booklibrary.bean;

import org.richfaces.JsfVersion;
import ua.softserve.booklibrary.entity.Author;
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
import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
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
    private Author newAuthor = new Author();
    private Author currentAuthor;

    private Long currentAuthorId;
    private String byRating;
    private String title;
    private String id;

    private Map<Author, Boolean> checkMap = new HashMap<>();


    public void loadData() {
        try {
            if (currentAuthor == null) {
                currentAuthor = authorManager.findByPk(Long.parseLong(getId()));
                currentAuthorId = currentAuthor.getId();
            }
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
        } catch (AlreadyExistException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save unsuccessful", e.getMessage()));
        }
        newAuthor = new Author();
        initAuthors();
    }

    public void update() {
        try {
            authorManager.update(currentAuthor);
        } catch (AlreadyExistException | EntityNotFoundException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update unsuccessful", e.getMessage()));
        }
        initAuthors();
    }

    public void remove() {
        try {
            authorManager.removeByPk(currentAuthorId);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Remove unsuccessful", e.getMessage()));
        }
        initAuthors();
    }

    public void removeAll() {
        try {
            authorManager.removeAll(getConfirmList());
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
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

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    private void initAuthors() {
        authors = authorManager.findAll(getByRating());
        initCheckMap();
    }

    private void initCheckMap() {
        for (Author author : authors) {
            checkMap.put(author, Boolean.FALSE);
        }
    }

    public String getSelected() {
        String result = "";
        for (Map.Entry<Author, Boolean> entry : checkMap.entrySet()) {
            if (entry.getValue()) {
                result = result + ", " + entry.getKey().getId();
            }
        }
        if (result.length() == 0) {
            return "";
        } else {
            return result.substring(2);
        }
    }

    public List<Author> getConfirmList() {
        List<Author> authors = new ArrayList<>();
        for (Map.Entry<Author, Boolean> entry : checkMap.entrySet()) {
            if (entry.getValue()) {
                authors.add(entry.getKey());
            }
        }
        System.err.println("================" + authors.size());
        return authors;
    }

    public Map<Author, Boolean> getCheckMap() {
        return checkMap;
    }

    public void setCheckMap(Map<Author, Boolean> checkMap) {
        this.checkMap = checkMap;
    }

    public Author getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(Author newAuthor) {
        this.newAuthor = newAuthor;
    }

    public void setCurrentAuthorId(Long currentAuthorId) {
        this.currentAuthorId = currentAuthorId;
    }

    public Long getCurrentAuthorId() {
        return currentAuthorId;
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
            UIComponent comp = fc.getViewRoot().findComponent("form2:editGrid");

            ((EditableValueHolder) comp.findComponent("form2:firstName")).resetValue();
            ((EditableValueHolder) comp.findComponent("form2:secondName")).resetValue();
        }
    }

    public void resetAddValues() {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent comp = fc.getViewRoot().findComponent("form2:createGrid");

        ((EditableValueHolder) comp.findComponent("form2:addFirstName")).resetValue();
        ((EditableValueHolder) comp.findComponent("form2:addSecondName")).resetValue();
    }
}
