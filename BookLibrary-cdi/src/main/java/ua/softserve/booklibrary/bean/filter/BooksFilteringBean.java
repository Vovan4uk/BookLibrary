package ua.softserve.booklibrary.bean.filter;

import org.apache.commons.lang3.StringUtils;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class BooksFilteringBean implements Serializable {
	private static final long serialVersionUID = -3725901188975943276L;
	private String authorFilter = "";
	private Boolean result;

	@EJB
	private AuthorManager authorManager;

	public boolean getFilterAuthorImpl(Object current) {    // todo: refactoring! - fixed
		Book currentBook = (Book) current;
		if (StringUtils.isEmpty(authorFilter)) {
			result = true;
		} else {
			for (Author author : currentBook.getAuthors()) {
				result = StringUtils.containsIgnoreCase((author.getFirstName() + " " + author.getSecondName()), authorFilter);
			}
		}
		return result;
	}

	public String getAuthorFilter() {
		return authorFilter;
	}

	public void setAuthorFilter(String authorFilter) {
		this.authorFilter = authorFilter;
	}
}