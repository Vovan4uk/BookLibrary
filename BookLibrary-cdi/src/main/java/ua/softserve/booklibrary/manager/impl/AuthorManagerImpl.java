package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.dao.home.BookHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.entity.Review;
import ua.softserve.booklibrary.exception.AlreadyExistException;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Named
@Stateless
public class AuthorManagerImpl implements AuthorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorManager.class);

    @EJB
    private AuthorHome authorHome;
    @EJB
    private AuthorFacade authorFacade;
    @EJB
    private BookHome bookHome;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Author save(Author author) throws AlreadyExistException {
        if (authorFacade.isAuthorExist(author)) {
            String errorMessage = "Save unsuccessful. Author is already exist";
            LOGGER.error(errorMessage);
            throw new AlreadyExistException(errorMessage);
        }
        LOGGER.debug("Save new Author {}", author);
        return authorHome.save(author);
    }

    @Override
    public Author update(Author author) throws AlreadyExistException {
        if (authorFacade.isAuthorExist(author)) {
            String errorMessage = "Update unsuccessful. Author is already exist";
            LOGGER.error(errorMessage);
            throw new AlreadyExistException(errorMessage);
        }
        LOGGER.debug("Update Author {}", author);
        return authorHome.update(author);
    }

    @Override
    public void removeByPk(Long id) {
        LOGGER.debug("Remove Author by primary key: {}", id);
        authorHome.removeByPk(id);
    }

    @Override
    public void removeAll(List<Author> authors) {
        Set<Book> books = new HashSet<>();
        for (Author author : authors) {
            books.addAll(author.getBooks());
        }
        LOGGER.debug("Remove list Books: {}", books);
        if (!books.isEmpty()) {
            bookHome.removeAll(books);
        }

        LOGGER.debug("Remove list Authors: {}", authors);
        authorHome.removeAll(new HashSet<>(authors));
    }

    @Override
    public Author findByPk(Long id) {
        LOGGER.debug("Find author by primary key: {}", id);
        return initBookList(authorFacade.findByPk(id));
    }

    @Override
    public List<Author> findAll() {
        LOGGER.debug("Find all authors");
        return initAuthorList(authorFacade.findAll());
    }

    @Override
    public List<Author> findAll(String byRating) {
        LOGGER.debug("Find authors by rating");
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
                LOGGER.error("Rating {} doesn't illegal", rating);
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            resultList = findAll();
        }
        return resultList;
    }

    @Override
    public List<Author> findAuthorsByRating(Integer minRating) {
        LOGGER.debug("Find authors with rating {}", minRating);
        return initAuthorList(authorFacade.findAuthorsByRating(minRating));
    }

    @Override
    public List<Author> findAuthorsWithoutRating() {
        LOGGER.debug("Find authors without rating");
        return initAuthorList(authorFacade.findAuthorsWithoutRating());
    }

    private List<Author> initAuthorList(List<Author> authors) {
        LOGGER.debug("Initialize author list (Books is lazy init)");
        for (Author author : authors) {
            author.getBooks().size();
        }
        return authors;
    }

    private Author initBookList(Author author) {
        LOGGER.debug("Initialize book list (Reviews is lazy init)");
        for (Book book : author.getBooks()) {
            book.getReviews().size();
        }
        return author;
    }
}
