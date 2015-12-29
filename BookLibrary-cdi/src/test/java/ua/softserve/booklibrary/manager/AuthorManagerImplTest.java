package ua.softserve.booklibrary.manager;

import org.apache.commons.lang3.math.NumberUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.facade.impl.AuthorFacadeImpl;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.manager.impl.AuthorManagerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorManagerImplTest {

	@Mock
	private AuthorFacade authorFacadeMock;

	@InjectMocks
	private final AuthorManager authorManager = new AuthorManagerImpl();

	private Author author1;
	private Author author2;
	private Author author3;
	private Author author4;
	private Author author5;
	private Author author6;
	private List<Author> authors;

	@BeforeClass
	public void setUp() {

		author1 = new Author();
		author1.setAverageRating(3D);

		author2 = new Author();
		author2.setAverageRating(3D);

		author3 = new Author();
		author3.setAverageRating(4D);

		author4 = new Author();
		author4.setAverageRating(4D);

		author5 = new Author();
		author4.setAverageRating(0D);

		author6 = new Author();
		author4.setAverageRating(0D);

		authors = new ArrayList<>();
		authors.add(author1);
		authors.add(author2);
		authors.add(author3);
		authors.add(author4);
		authors.add(author5);
		authors.add(author6);

		authorFacadeMock = mock(AuthorFacadeImpl.class);
		MockitoAnnotations.initMocks(this);
	}

	@DataProvider(name = "testFindAuthorsWithRatingDataProvider")
	public Object[][] testFindAuthorsWithRatingDataProvider() {
		return new Object[][]{
				{authors, "8", 6},
				{Arrays.asList(author1, author2), "3", 2},
				{Arrays.asList(author3, author4), "4", 2},
				{Arrays.asList(author5, author6), "0", 2},
				{Arrays.asList(author5, author6), "InvalidString", 2}
		};
	}

	@Test(dataProvider = "testFindAuthorsWithRatingDataProvider")
	public void testFindAuthorsWithRating(List<Author> mockedAuthors, String rating, int expectedSize) {
		when(authorFacadeMock.findAll()).thenReturn(mockedAuthors);
		when(authorFacadeMock.findAuthorsWithoutRating()).thenReturn(mockedAuthors);
		when(authorFacadeMock.findAuthorsByRating(NumberUtils.toInt(rating))).thenReturn(mockedAuthors);

		List<Author> result = authorManager.findByRating(rating);
		Assert.assertNotNull(result);
		Assert.assertEquals(expectedSize, result.size());
	}
}