package ua.softserve.booklibrary.manager;

import org.apache.commons.lang3.math.NumberUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.softserve.booklibrary.dao.facade.BookFacade;
import ua.softserve.booklibrary.dao.facade.impl.BookFacadeImpl;
import ua.softserve.booklibrary.entity.Book;
import ua.softserve.booklibrary.manager.impl.BookManagerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookManagerImplTest {

	@Mock
	private BookFacade bookFacadeMock;

	@InjectMocks
	private final BookManager bookManager = new BookManagerImpl();

	private Book book1;
	private Book book2;
	private Book book3;
	private Book book4;
	private Book book5;
	private Book book6;
	private List<Book> books;

	@BeforeClass
	public void setUp() {

		book1 = new Book();
		book1.setAverageRating(3D);

		book2 = new Book();
		book2.setAverageRating(3D);

		book3 = new Book();
		book3.setAverageRating(5D);

		book4 = new Book();
		book4.setAverageRating(5D);

		book5 = new Book();
		book5.setAverageRating(0D);

		book6 = new Book();
		book6.setAverageRating(0D);

		books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		books.add(book3);
		books.add(book4);
		books.add(book5);
		books.add(book6);

		bookFacadeMock = mock(BookFacadeImpl.class);
		MockitoAnnotations.initMocks(this);
	}

	@DataProvider(name = "testFindBooksWithRatingDataProvider")
	public Object[][] testFindBooksWithRatingDataProvider() {
		return new Object[][]{
				{books, "8", 6},
				{Arrays.asList(book1, book2), "3", 2},
				{Arrays.asList(book3, book4), "5", 2},
				{Arrays.asList(book5, book6), "0", 2},
				{Arrays.asList(book5, book6), "InvalidString", 2}
		};
	}

	@Test(dataProvider = "testFindBooksWithRatingDataProvider")
	public void testFindBooksWithRating(List<Book> mockedBooks, String rating, int expectedSize) {
		when(bookFacadeMock.findAll()).thenReturn(mockedBooks);
		when(bookFacadeMock.findBooksWithoutRating()).thenReturn(mockedBooks);
		when(bookFacadeMock.findBooksByRating(NumberUtils.toInt(rating))).thenReturn(mockedBooks);

		List<Book> result = bookManager.findByRating(rating);
		Assert.assertNotNull(result);
		Assert.assertEquals(expectedSize, result.size());
	}
}