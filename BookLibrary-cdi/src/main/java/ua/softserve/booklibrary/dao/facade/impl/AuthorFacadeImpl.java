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
    public boolean isAuthorExist(Author author) {
        if (author.getId() != null) {
            return isCurrentAuthorExist(author);
        } else {
            return isNewAuthorExist(author);
        }
    }

    private boolean isNewAuthorExist(Author author) {
        if (author.getSecondName().isEmpty()) {
            return em.createNamedQuery("Author.isAuthorsExistByFirstName", Boolean.class)
                    .setParameter("firstName", author.getFirstName())
                    .getSingleResult();
        } else {
            return em.createNamedQuery("Author.isAuthorsExistByFirstAndSecondName", Boolean.class)
                    .setParameter("firstName", author.getFirstName())
                    .setParameter("secondName", author.getSecondName())
                    .getSingleResult();
        }
    }

    private boolean isCurrentAuthorExist(Author author) {
        if (author.getSecondName().isEmpty()) {
            return em.createNamedQuery("Author.isAuthorsExistByFirstNameWithId", Boolean.class)
                    .setParameter("firstName", author.getFirstName())
                    .setParameter("id", author.getId())
                    .getSingleResult();
        } else {
            return em.createNamedQuery("Author.isAuthorsExistByFirstAndSecondNameWithId", Boolean.class)
                    .setParameter("firstName", author.getFirstName())
                    .setParameter("secondName", author.getSecondName())
                    .setParameter("id", author.getId())
                    .getSingleResult();
        }
    }
}