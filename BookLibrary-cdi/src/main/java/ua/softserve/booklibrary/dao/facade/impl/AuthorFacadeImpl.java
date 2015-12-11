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
            LOGGER.debug("Find new author by 'firstName' {}", author.getFirstName());
            return em.createNamedQuery("Author.isAuthorsExistByFirstName", Boolean.class)
                    .setParameter("firstName", author.getFirstName())   // todo: author.getFirstName() == null ?
                    .getSingleResult();
        } else {
            LOGGER.debug("Find new author by 'firstName' {} and 'secondName' {}", author.getFirstName(), author.getSecondName());
            return em.createNamedQuery("Author.isAuthorsExistByFirstAndSecondName", Boolean.class)
                    .setParameter("firstName", author.getFirstName())   // todo: author.getFirstName() == null ?
                    .setParameter("secondName", author.getSecondName()) // todo: author.getSecondName() == null ?
                    .getSingleResult();
        }
    }

    private boolean isCurrentAuthorExist(Author author) {
        if (author.getSecondName().isEmpty()) {
            LOGGER.debug("Find current author by 'firstName' {} and 'id' {}", author.getFirstName(), author.getId());
            return em.createNamedQuery("Author.isAuthorsExistByFirstNameWithId", Boolean.class)
                    .setParameter("firstName", author.getFirstName())   // todo: author.getFirstName() == null ?
                    .setParameter("id", author.getId())                 // todo: author.getId() == null ?
                    .getSingleResult();
        } else {
            LOGGER.debug("Find current author by 'firstName' {} and 'secondName' {} and 'id' {}", author.getFirstName(), author.getSecondName(), author.getId());
            return em.createNamedQuery("Author.isAuthorsExistByFirstAndSecondNameWithId", Boolean.class)
                    .setParameter("firstName", author.getFirstName())       // todo: author.getFirstName() == null ?
                    .setParameter("secondName", author.getSecondName())     // todo: author.getSecondName() == null ?
                    .setParameter("id", author.getId())                     // todo: author.getId() == null ?
                    .getSingleResult();
        }
    }
}