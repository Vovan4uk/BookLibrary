package ua.softserve.booklibrary.dao.home;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.home.impl.AuthorHomeImpl;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorHomeTest {

	private AuthorFacade mockedAuthorFacade;
	private AuthorHome authorHomeMock;

/*
	@BeforeClass
	public void setUp() throws Exception {
		mockedAuthorFacade = mock(AuthorFacade.class);


		Author author1 = new Author();
		author1.setSecondName("SecondNameFirstAuthor");
		author1.setFirstName("FirstNameFirstAuthor");
		author1.setId(1);
		author1.setRating(1.00);

		Author author2 = new Author();
		author2.setSecondName("SecondNameSecondAuthor");
		author2.setFirstName("FirstNameSecondAuthor");
		author2.setId(2);
		author2.setRating(2.00);

		when(mockedAuthorFacade.findAll()).thenReturn(Arrays.asList(author1, author2));
		when(mockedAuthorFacade.findById(1)).thenReturn(author1);
		when(mockedAuthorFacade.findById(2)).thenReturn(author2);
	}

	@Test
	public void testGetAllAuthors() throws Exception {
		List<Author> allAuthors = mockedAuthorFacade.findAll();
		Assert.assertEquals(2, allAuthors.size());

	}

	@Test
	public void testGetAuthor() throws Exception {
		Author author = mockedAuthorFacade.findById(2);
		Assert.assertNotNull(author);
		Assert.assertEquals("FirstNameSecondAuthor", author.getFirstName());
		Assert.assertEquals("SecondNameSecondAuthor", author.getSecondName());
	}

	@Test
	public void testAddAuthor() throws Exception {
		Author author = new Author();
		author.setId(3);
		author.setFirstName("FirstNameThirdAuthor");
		author.setSecondName("SecondtNameThirdAuthor");
		author.setRating(3.00);
		doNothing().when(mockedAuthorHome).create(any(Author.class));
		mockedAuthorHome.create(author);
	}

	@Test
	public void testUpdateAuthor() throws Exception {
		String authorName = mockedAuthorFacade.findById(1).getFirstName();
		Assert.assertNotNull(authorName);
		Assert.assertEquals("FirstNameFirstAuthor", authorName);
	}

	@Test(expectedExceptions = IndexOutOfBoundsException.class)
	public void testNullPointerException() {
		List<Author> allAuthors = mockedAuthorFacade.findAll();
		allAuthors.get(100);
	}
*/

	@Test
	public void testExceptionw() {
		mockedAuthorFacade = mock(AuthorFacade.class);
		Integer count = mockedAuthorFacade.findAuthorsByRating(4).size();
		Assert.assertEquals(0, count, 0);
	}

	/*
		@Test(expectedExceptions = LibraryException.class)
		public void testRemoveByNullPk() {
			authorHomeMock.removeByPk(null);
		}
	*/
	@Test(expectedExceptions = Throwable.class)
	public void testSave() {
		authorHomeMock = mock(AuthorHomeImpl.class);
		Author author = new Author();
		author.setFirstName("CreateUniqueFirstName#?123456789");
		author.setSecondName("CreateUniqueSecondName#?123456789");
		author.setAverageRating(4D);
		authorHomeMock.save(null);
	}

}
