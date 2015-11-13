package ua.softserve.booklibrary.service.serviceImpl;

import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.service.AuthorService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class AuthorServiceImpl implements AuthorService {

    public static final Class AUTHOR_CLASS = Author.class;

    @PersistenceContext(unitName = "OracleDS")
    private EntityManager em;

    public Author findAuthorById(Long id) {
        return (Author) em.find(AUTHOR_CLASS, id);
    }

    @SuppressWarnings("unchecked")
    public List<Author> getAllAuthors() {
        return em.createQuery("select a from Author a").getResultList();
    }

    public void removeAuthor(Author author) {
        em.remove(author);
    }

    public void saveAuthor(Author author) {
        Logger logger = Logger.getLogger("TestInterceptor");
        logger.info("++++++++++++++++++++++++++++++++++++Succsess+++++++++++++++++++++++++++++++++++");
        em.persist(author);
    }
}
