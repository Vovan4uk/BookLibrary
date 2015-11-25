package ua.softserve.booklibrary.dao.facade.impl;

import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.entity.Author;

import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class AuthorFacadeImpl extends GenericFacadeImpl<Author> implements AuthorFacade {
    public AuthorFacadeImpl() {
        super(Author.class);
    }

    @Override
    public List<Author> findAuthorsByRating(Integer minRating) {
        Integer maxRating = minRating + 1;
        return em.createNamedQuery("Author.findAuthorsByRating", Author.class).setParameter("minRating", minRating.doubleValue()).setParameter("maxRating", maxRating.doubleValue()).getResultList();
    }

    @Override
    public List<Author> findAuthorsWithoutRating() {
        return em.createNamedQuery("Author.findAuthorsWithoutRating", Author.class).getResultList();
    }
}
