package ua.softserve.booklibrary.rest.client;

import ua.softserve.booklibrary.entity.Book;

import java.util.List;

public interface BookClientService {

	Integer countBooksByRating(String rating);

	Integer countBooksWithoutRating();

	Integer countAllBooks();

	Integer countAllReviews();

	List<Book> findMostPopular();
}