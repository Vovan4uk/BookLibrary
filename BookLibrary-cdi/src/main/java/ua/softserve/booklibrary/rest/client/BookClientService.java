package ua.softserve.booklibrary.rest.client;

public interface BookClientService {

	Integer countBooksByRating(String rating);

	Integer countBooksWithoutRating();

}
