package ua.softserve.booklibrary.manager;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.impl.AuthorManagerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertSame;

public class TestAuthorManagerImpl {

	private final Author author = new Author();
	private final List<Author> authors = new ArrayList<>();
	private AuthorFacade authorFacade;
	private AuthorManager authorManager;

	@BeforeClass
	public void setUp() {
		authorFacade = mock(AuthorFacade.class);
		authorManager = new AuthorManagerImpl();

		//author
		author.setId(1l);

		//authors
		for (long i = 2; i < 12; i++) {
			Author a = new Author();
			a.setId(i);
			authors.add(a);
		}

	}

	@Test
	public void testFindAll() {
		Author author1 = new Author();
		author1.setFirstName("FirstNameTest");
		author1.setSecondName("SecondNameTest");

		Author author2 = new Author();
		author2.setFirstName("FirstNameTest2");
		author2.setSecondName("SecondNameTest2");

		List<Author> authors = new ArrayList<>();
		authors.add(author1);
		authors.add(author2);

//        assertSame(1, 1);

		when(authorFacade.findAll()).thenReturn(authors);

		List<Author> result = authorManager.findAll();


		assertSame(authors, result);

	}
}
