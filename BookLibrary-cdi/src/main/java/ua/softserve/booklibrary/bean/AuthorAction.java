package ua.softserve.booklibrary.bean;

import org.apache.commons.lang3.math.NumberUtils;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.client.AuthorClientService;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
	private transient AuthorManager authorManager;
	@Inject
	private transient AuthorClientService authorClientService;

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
				currentAuthor = authorClientService.findAuthorByPk(NumberUtils.toLong(getId()));
				currentAuthorId = currentAuthor.getId();
			}
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author with primary key " + getId() + " doesn't exist.", e.getMessage()));
			currentAuthor = null;
		}
	}

	public void save() {
		try {
			authorClientService.saveAuthor(newAuthor);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author can't be create. Author is already exist.", e.getMessage()));
		}
		newAuthor = new Author();
		initAuthors();
	}

	public void update() {
		try {
			authorClientService.updateAuthor(currentAuthor);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author can't be update. Author is already exist.", e.getMessage()));
		}
		initAuthors();
	}

	public void remove() {
		try {
			authorClientService.removeAuthor(currentAuthorId);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Author can't be delete.", e.getMessage()));
		}
		initAuthors();
	}

	public void removeAll() {
		try {
			authorManager.removeAll(getConfirmList());
		} catch (EJBException | LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authors can't be delete.", e.getMessage()));
		}
		initAuthors();
	}

	public Integer getCountAuthorsByRating(String minRating) {
		try {
			return authorClientService.countAuthorsByRating(minRating);
		} catch (EJBException | LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can't find authors.", e.getMessage()));
			return 0;
		}
	}

	public Integer getCountAuthorsWithoutRating() {
		try {
			return authorClientService.countAuthorsWithoutRating();
		} catch (EJBException | LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can't find authors.", e.getMessage()));
			return 0;
		}
	}

	public String getTitle() {
		Integer rating = NumberUtils.toInt(FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap()
				.get("byrating"), -1);
		if (rating >= 0 && rating <= 5) {
			title = "by rating " + rating;
		}
		return title;
	}

	public String getId() {
		if (id == null) {
			id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		}
		return id;
	}

	public String getByRating() {
		if (byRating == null) {
			byRating = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byrating");
		}
		return byRating;
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
		try {
			if (getByRating() == null) {
				authors = authorClientService.findAllAuthors();
			} else {
				authors = authorClientService.findAuthorsByRating(getByRating());
			}
		} catch (EJBException | LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Can't find authors.", e.getMessage()));
			authors = new ArrayList<>();
		}
		initCheckMap();
	}

	public void initCheckMap() {
		checkMap.clear();
		for (Author author : authors) {
			checkMap.put(author, Boolean.FALSE);
		}
	}

	public List<Author> getConfirmList() {
		List<Author> authors = new ArrayList<>();
		for (Map.Entry<Author, Boolean> entry : checkMap.entrySet()) {
			if (entry.getValue()) {
				authors.add(entry.getKey());
			}
		}
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
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent comp = fc.getViewRoot().findComponent("form2:editGrid");

		((EditableValueHolder) comp.findComponent("form2:firstName")).resetValue();
		((EditableValueHolder) comp.findComponent("form2:secondName")).resetValue();
	}

	public void resetAddValues() {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent comp = fc.getViewRoot().findComponent("form2:createGrid");

		((EditableValueHolder) comp.findComponent("form2:addFirstName")).resetValue();
		((EditableValueHolder) comp.findComponent("form2:addSecondName")).resetValue();
	}
}
