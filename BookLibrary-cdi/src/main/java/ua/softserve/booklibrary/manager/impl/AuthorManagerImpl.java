package ua.softserve.booklibrary.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.AuthorManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import java.util.List;

@Named
@Stateless
public class AuthorManagerImpl implements AuthorManager {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorManager.class);

    @EJB
    AuthorHome authorHome;

    @EJB
    AuthorFacade authorFacade;

    @Override
    public void save(Author entity) {
        LOG.debug("Save new Author ", entity);
        authorHome.save(entity);
    }

    @Override
    public Author update(Author entity) {
        return null;
    }

    @Override
    public void removeByPk(Long id) {

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
        List<Author> authors = authorFacade.findAll();
        for (Author author : authors) {
            author.getBooks().size();
        }
        return authors;
    }
}
