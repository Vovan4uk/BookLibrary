package ua.softserve.booklibrary.bean;

import org.apache.commons.lang3.math.NumberUtils;
import org.richfaces.JsfVersion;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.client.AuthorClientService;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
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
	private AuthorManager authorManager;    //todo: transient
	@EJB
	private AuthorClientService authorClientService;    //todo: transient

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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
			currentAuthor = null;
		}
	}

	public void save() {
		try {
			authorClientService.saveAuthor(newAuthor);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		newAuthor = new Author();
		initAuthors();
	}

	public void update() {
		try {
			authorClientService.updateAuthor(currentAuthor);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		initAuthors();
	}

	public void remove() {
		try {
			authorClientService.removeAuthor(currentAuthorId);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		initAuthors();
	}

	public void removeAll() {
		try {
			authorManager.removeAll(getConfirmList());
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		initAuthors();
	}

	public Integer getCountAuthorsByRating(Integer minRating) {
		return authorManager.countAuthorsByRating(minRating);
	}

	public Integer getCountAuthorsWithoutRating() {
		return authorManager.countAuthorsWithoutRating(); // todo: it's wrong !! - fixed (uses namedQueries which return count of authors)
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
		if (getByRating() == null) {
			authors = authorClientService.findAllAuthors();
		} else {
			authors = authorClientService.findAuthorsByRating(getByRating());
		}
		initCheckMap();
	}

	private void initCheckMap() {
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
