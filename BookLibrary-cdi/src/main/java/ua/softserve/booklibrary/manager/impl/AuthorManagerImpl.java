package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class AuthorManagerImpl implements AuthorManager {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorManager.class);

    @EJB
    private AuthorHome authorHome;
    @EJB
    private AuthorFacade authorFacade;

    @Override
    public void save(Author entity) {
        LOG.debug("Save new Author ", entity);
        authorHome.save(entity);
    }

    @Override
    public Author update(Author entity) {
        return authorHome.update(entity);
    }

    @Override
    public void removeByPk(Long id) {
        try {
            authorHome.removeByPk(id);
        } catch (IllegalArgumentException | EJBException e) {
            //Show some message
        }
    }

    @Override
    public void removeAll(List<Author> entities) {

    }

    @Override
    public Author findByPk(Long id) {
        return null;
    }

    @Override
    public List<Author> findAll() {
        return initAuthorList(authorFacade.findAll());
    }

    private List<Author> getAuthors() {
        String byRating = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("byrating");
        List<Author> resultList;
        try {
            Integer rating = Integer.parseInt(byRating);
            if (rating == 0) {
                resultList = findAuthorsWithoutRating();
            } else if (rating > 0 && rating <= 5) {
                resultList = findAuthorsByRating(rating);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            resultList = authorFacade.findAll();
        }
        return resultList;
    }

    @Override
    public List<Author> findAuthorsByRating(Integer minRating) {
        return initAuthorList(authorFacade.findAuthorsByRating(minRating));
    }

    @Override
    public List<Author> findAuthorsWithoutRating() {
        return initAuthorList(authorFacade.findAuthorsWithoutRating());
    }

    private List<Author> initAuthorList(List<Author> authors) {
        for (Author author : authors) {
            author.getBooks().size();
        }
        return authors;
    }
}
