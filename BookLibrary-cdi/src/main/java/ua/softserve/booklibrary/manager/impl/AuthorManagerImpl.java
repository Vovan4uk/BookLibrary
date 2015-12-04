package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.exception.AlreadyExistException;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class AuthorManagerImpl implements AuthorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorManager.class);

    @EJB
    private AuthorHome authorHome;
    @EJB
    private AuthorFacade authorFacade;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Author save(Author author) throws AlreadyExistException {
        if (isAlreadyExist(author)) {
            String errorMessage = "Save unsuccessful. Author is already exist";
            LOGGER.error(errorMessage);
            throw new AlreadyExistException(errorMessage);
        }
        LOGGER.debug("Save new Author {}", author);
        return authorHome.save(author);
    }

    @Override
    public Author update(Author author) throws AlreadyExistException {
        LOGGER.debug("Update for author {}", author);
        if (author.getId() == null) {
            String errorMessage = "Save unsuccessful. Author is already exist";
            LOGGER.error(errorMessage);
            throw new AlreadyExistException(errorMessage);
        }
        return authorHome.update(author);
    }

    private boolean isAlreadyExist(Author author) {
        boolean result;
        if (author.getSecondName().isEmpty()) {
            LOGGER.debug("Save new Author {}", author);
            result = (authorFacade.findByFirstName(author.getFirstName()) != null);
        } else {
            result = (authorFacade.findBySecondAndFirstName(author.getSecondName(), author.getFirstName()) != null);
        }
        return result;
    }

    @Override
    public void removeByPk(Long id) {
        LOGGER.debug("Remove Author by primary key: {}", id);
        authorHome.removeByPk(id);
    }

    @Override
    public void removeAll(List<Author> entities) {

    }

    @Override
    public Author findByPk(Long id) {
        LOGGER.debug("Find author by primary key: {}", id);
        return initBookList(authorFacade.findByPk(id));
    }

    @Override
    public List<Author> findAll() {
        return null;
    }

    @Override
    public List<Author> findAll(String byRating) {
        return initAuthorList(getAuthors(byRating));
    }

    private List<Author> getAuthors(String byRating) {
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

    private Author initBookList(Author author) {
        for (Book book : author.getBooks()) {
            book.getReviews().size();
        }
        return author;
    }
}
