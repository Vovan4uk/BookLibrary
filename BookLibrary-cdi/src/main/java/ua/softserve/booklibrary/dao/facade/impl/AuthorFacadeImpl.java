package ua.softserve.booklibrary.dao.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

/**
 * Author Facade Bean
 *
 * @see ua.softserve.booklibrary.dao.facade.AuthorFacade
 */
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
		if (minRating == null || minRating < 1 || minRating > 5) {
			String message = "MinRating '" + minRating + "' is not valid.";
			LOGGER.error(message);
			throw new LibraryException(message);
		}
		Integer maxRating = minRating + 1;
		LOGGER.debug("Find authors by rating between ({} and {})", minRating, maxRating);
		List<Author> resultList = em.createNamedQuery("Author.findAuthorsByRating", Author.class)
				.setParameter("minRating", minRating.doubleValue())
				.setParameter("maxRating", maxRating.doubleValue())
				.getResultList();
		LOGGER.debug("Result list: {}", resultList);
		return resultList;
	}

	@Override
	public List<Author> findAuthorsWithoutRating() {
		LOGGER.debug("Find authors without rating");
		List<Author> resultList = em.createNamedQuery("Author.findAuthorsWithoutRating", Author.class)
				.getResultList();
		LOGGER.debug("Result list: {}", resultList);
		return resultList;
	}

	@Override
	public Integer countAuthorsByRating(String minRatingString) {
		Integer minRating = NumberUtils.toInt(minRatingString);
		if (minRating < 1 || minRating > 5) {
			String message = "MinRating '" + minRatingString + "' is not valid.";
			LOGGER.error(message);
			throw new LibraryException(message);
		}
		Integer maxRating = minRating + 1;
		LOGGER.debug("Count authors by rating between ({} and {})", minRating, maxRating);
		Integer result = em.createNamedQuery("Author.countAuthorsByRating", Number.class)
				.setParameter("minRating", minRating.doubleValue())
				.setParameter("maxRating", maxRating.doubleValue())
				.getSingleResult()
				.intValue();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public Integer countAuthorsWithoutRating() {
		LOGGER.debug("Count authors without rating");
		Integer result = em.createNamedQuery("Author.countAuthorsWithoutRating", Number.class)
				.getSingleResult()
				.intValue();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public Integer countAllAuthors() {
		LOGGER.debug("Count all authors");
		Integer result = em.createNamedQuery("Author.countAllAuthors", Number.class)
				.getSingleResult()
				.intValue();
		LOGGER.debug("Result: {}", result);
		return result;
	}

	@Override
	public boolean isAuthorExist(Author author) {
		if (StringUtils.isEmpty(author.getFirstName())) {
			String message = "Author '" + author + "' first name is empty.";
			LOGGER.error(message);
			throw new LibraryException(message);
		}
		if (author.getId() != null) {
			return isCurrentAuthorExist(author);
		} else {
			return isNewAuthorExist(author);
		}
	}

	private boolean isNewAuthorExist(Author author) {
		if (StringUtils.isEmpty(author.getSecondName())) {
			LOGGER.debug("Find new author by 'firstName' {}", author.getFirstName());
			return em.createNamedQuery("Author.isAuthorsExistByFirstName", Boolean.class)
					.setParameter("firstName", author.getFirstName())   // todo: author.getFirstName() == null ? - fixed ('if' and throw exception in parent method)
					.getSingleResult();
		} else {
			LOGGER.debug("Find new author by 'firstName' {} and 'secondName' {}", author.getFirstName(), author.getSecondName());
			return em.createNamedQuery("Author.isAuthorsExistByFirstAndSecondName", Boolean.class)
					.setParameter("firstName", author.getFirstName())   // todo: author.getFirstName() == null ? - fixed ('if' and throw exception in parent method)
					.setParameter("secondName", author.getSecondName()) // todo: author.getSecondName() == null ? - fixed ('if' in current method)
					.getSingleResult();
		}
	}

	private boolean isCurrentAuthorExist(Author author) {
		if (StringUtils.isEmpty(author.getSecondName())) {
			LOGGER.debug("Find current author by 'firstName' {} and 'id' {}", author.getFirstName(), author.getId());
			return em.createNamedQuery("Author.isAuthorsExistByFirstNameWithId", Boolean.class)
					.setParameter("firstName", author.getFirstName())   // todo: author.getFirstName() == null ? - fixed ('if' and throw exception in parent method)
					.setParameter("id", author.getId())                 // todo: author.getId() == null ? - fixed ('if' in parent method)
					.getSingleResult();
		} else {
			LOGGER.debug("Find current author by 'firstName' {} and 'secondName' {} and 'id' {}", author.getFirstName(), author.getSecondName(), author.getId());
			return em.createNamedQuery("Author.isAuthorsExistByFirstAndSecondNameWithId", Boolean.class)
					.setParameter("firstName", author.getFirstName())       // todo: author.getFirstName() == null ? - fixed ('if' and throw exception in parent method)
					.setParameter("secondName", author.getSecondName())     // todo: author.getSecondName() == null ? - fixed ('if' in current method)
					.setParameter("id", author.getId())                     // todo: author.getId() == null ? - fixed ('if' in parent method)
					.getSingleResult();
		}
	}
}