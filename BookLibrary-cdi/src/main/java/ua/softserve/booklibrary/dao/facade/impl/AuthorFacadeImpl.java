package ua.softserve.booklibrary.dao.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AuthorFacadeImpl extends GenericFacadeImpl<Author> implements AuthorFacade {
    public AuthorFacadeImpl() {
        super(Author.class);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorFacadeImpl.class);

    @Override
    public List<Author> findAuthorsByRating(Integer minRating) {
        Integer maxRating = minRating + 1;
        LOGGER.debug("Find authors by rating between ({} and {})", minRating, maxRating);
        List<Author> resultList = em.createNamedQuery("Author.findAuthorsByRating", Author.class).setParameter("minRating", minRating.doubleValue()).setParameter("maxRating", maxRating.doubleValue()).getResultList();
        LOGGER.debug("Result list: {}", resultList);
        return resultList;
    }

    @Override
    public List<Author> findAuthorsWithoutRating() {
        LOGGER.debug("Find authors without rating");
        List<Author> resultList = em.createNamedQuery("Author.findAuthorsWithoutRating", Author.class).getResultList();
        LOGGER.debug("Result list: {}", resultList);
        return resultList;
    }

    @Override
    public Author findBySecondAndFirstName(String secondName, String firstName) {
        Author author = null;
        LOGGER.debug("Find authors by second name '{}' and first name '{}'", secondName, firstName);
        List<Author> authors = em.createNamedQuery("Author.findBySecondAndFirstName", Author.class)
                .setParameter("secondName", secondName)
                .setParameter("firstName", firstName)
                .getResultList();
        if (!authors.isEmpty()) {
            author = authors.get(0);
        }
        LOGGER.debug("Result: {}", author);
        return author;
    }

    @Override
    public Author findByFirstName(String firstName) {
        Author author = null;
        LOGGER.debug("Find authors by first name '{}'", firstName);
        List<Author> authors = em.createNamedQuery("Author.findByFirstName", Author.class)
                .setParameter("firstName", firstName)
                .getResultList();
        if (!authors.isEmpty()) {
            author = authors.get(0);
        }
        LOGGER.debug("Result: {}", author);
        return author;
    }
}
