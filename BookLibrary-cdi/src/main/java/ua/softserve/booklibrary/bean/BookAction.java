package ua.softserve.booklibrary.bean;

import org.apache.commons.lang3.math.NumberUtils;
import org.richfaces.JsfVersion;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.manager.BookManager;
import ua.softserve.booklibrary.manager.ReviewManager;

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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Named
@ViewScoped
public class BookAction implements Serializable {

	private static final long serialVersionUID = 6489108625203081136L;

	@EJB
	private transient ReviewManager reviewManager;
	@EJB
	private transient BookManager bookManager;
	@EJB
	private transient AuthorManager authorManager;

	private List<Book> hotReleases;
	private List<Book> books;
	private Book newBook = new Book();
	private Review newReview = new Review();
	private Book currentBook;
	private Review currentReview;

	private Long currentBookId;
	private Long currentReviewId;
	private String byRating;
	private String title;
	private String id;

	private Map<Book, Boolean> checkMap = new HashMap<>();

	private List<Author> authors;
	private List<Long> selectedAuthorIds = new ArrayList<>();

	public void loadData() {
		try {
			if (currentBook == null) {
				currentBook = bookManager.findByPk(NumberUtils.toLong(getId()));
				currentBookId = currentBook.getId();
				initSelectedAuthorIds();
			}
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
			currentBook = null;
		}
	}

	public void save() {
		try {
			newBook.setAuthors(getSelectedAuthors());
			bookManager.save(newBook);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		newBook = new Book();
		selectedAuthorIds.clear();
		initBooks();
	}

	public void saveReview() {
		try {
			newReview.setRating(5);
			newReview.setBook(currentBook);
			reviewManager.save(newReview);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		newReview = new Review();
		currentBook = bookManager.findByPk(NumberUtils.toLong(getId()));
	}

	private Set<Author> getSelectedAuthors() {
		Set<Author> authors = new HashSet<>();
		for (Long id : selectedAuthorIds) {
			Author author = new Author();
			author.setId(id);
			authors.add(author);
		}
		return authors;
	}

	public void update() {
		try {
			currentBook.setAuthors(getSelectedAuthors());
			bookManager.update(currentBook);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		selectedAuthorIds.clear();
		initBooks();
	}


	public void updateReview() {
		try {
			currentReview.setRating(5);
			currentReview.setBook(currentBook);
			reviewManager.update(currentReview);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
	}



	public void remove() {
		try {
			bookManager.removeByPk(currentBookId);
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		initBooks();
	}

	public void removeAll() {
		try {
			bookManager.removeAll(getConfirmList());
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		initBooks();
	}

	public void removeReview() {
		try {
			reviewManager.removeByPk(currentReview.getId());
		} catch (LibraryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		currentBook = bookManager.findByPk(NumberUtils.toLong(getId()));
	}

	public Integer getCountBooksByRating(Integer minRating) {
		return bookManager.countBooksByRating(minRating);
	}

	public Integer getCountBooksWithoutRating() {
		return bookManager.countBooksWithoutRating();
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

	public List<Book> getBooks() {
		if (books == null) {
			initBooks();
		}
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	private void initBooks() {
		if (getByRating() == null) {
			books = bookManager.findAll();
		} else {
			books = bookManager.findAll(getByRating());
		}
		initCheckMap();
	}

	private void initCheckMap() {
		checkMap.clear();
		for (Book book : books) {
			checkMap.put(book, Boolean.FALSE);
		}
	}

	public List<Book> getConfirmList() {
		List<Book> books = new ArrayList<>();
		for (Map.Entry<Book, Boolean> entry : checkMap.entrySet()) {
			if (entry.getValue()) {
				books.add(entry.getKey());
			}
		}
		return books;
	}

	public Map<Book, Boolean> getCheckMap() {
		return checkMap;
	}

	public void setCheckMap(Map<Book, Boolean> checkMap) {
		this.checkMap = checkMap;
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

	public List<Long> getSelectedAuthorIds() {
		return selectedAuthorIds;
	}

	public void setSelectedAuthorIds(List<Long> selectedAuthorIds) {
		this.selectedAuthorIds = selectedAuthorIds;
	}

	public Book getNewBook() {
		return newBook;
	}

	public void setNewBook(Book newBook) {
		this.newBook = newBook;
	}

	public Review getNewReview() {
		return newReview;
	}

	public void setNewReview(Review newReview) {
		this.newReview = newReview;
	}

	public void setCurrentBookId(Long currentBookId) {
		this.currentBookId = currentBookId;
	}

	public Long getCurrentBookId() {
		return currentBookId;
	}

	public void setCurrentBook(Book currentBook) {
		this.currentBook = currentBook;
		initSelectedAuthorIds();
	}

	public Book getCurrentBook() {
		return currentBook;
	}

	public void setCurrentReviewId(Long currentReviewId) {
		this.currentReviewId = currentReviewId;
	}

	public Long getCurrentReviewId() {
		return currentReviewId;
	}

	public void setCurrentReview(Review currentReview) {
		this.currentReview = currentReview;
	}

	public Review getCurrentReview() {
		return currentReview;
	}

	private void initSelectedAuthorIds() {
		selectedAuthorIds.clear();
		if (!currentBook.getAuthors().isEmpty()) {
			for (Author author : currentBook.getAuthors()) {
				selectedAuthorIds.add(author.getId());
			}
		}
	}

	public List<Book> getHotReleases() {
		if (hotReleases == null) {
			hotReleases = bookManager.findHotReleases();
		}
		return hotReleases;
	}

	public void setHotReleases(List<Book> hotReleases) {
		this.hotReleases = hotReleases;
	}

	public List<Book> getLatestBooksByAuthorId(Long id, Integer count) {
		return bookManager.findLatestBooksByAuthorId(id, count);
	}

	public List<Book> getBestBooksByAuthorId(Long id, Integer count) {
		return bookManager.findBestBooksByAuthorId(id, count);
	}

	public void resetValues() {
		if (!JsfVersion.getCurrent().isCompliantWith(JsfVersion.JSF_2_2)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			UIComponent comp = fc.getViewRoot().findComponent("form2:editGrid");

			((EditableValueHolder) comp.findComponent("form2:name")).resetValue();
			((EditableValueHolder) comp.findComponent("form2:publisher")).resetValue();
			((EditableValueHolder) comp.findComponent("form2:publishedDate")).resetValue();
			((EditableValueHolder) comp.findComponent("form2:isbn")).resetValue();
			((EditableValueHolder) comp.findComponent("form2:authors")).resetValue();
		}
	}

	public void resetAddValues() {
		selectedAuthorIds.clear();
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent comp = fc.getViewRoot().findComponent("form2:createGrid");
		((EditableValueHolder) comp.findComponent("form2:addName")).resetValue();
		((EditableValueHolder) comp.findComponent("form2:addPublisher")).resetValue();
		((EditableValueHolder) comp.findComponent("form2:addPublishedDate")).resetValue();
		((EditableValueHolder) comp.findComponent("form2:addIsbn")).resetValue();
		((EditableValueHolder) comp.findComponent("form2:addAuthors")).resetValue();
	}

	public void resetAddReview() {
	}

}