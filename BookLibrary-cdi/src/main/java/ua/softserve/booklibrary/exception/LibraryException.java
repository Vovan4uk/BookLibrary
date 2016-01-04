package ua.softserve.booklibrary.exception;

public class LibraryException extends RuntimeException {

	private static final long serialVersionUID = -4705706628002206962L;

	public LibraryException(String message) {
		super(message);
	}

	public LibraryException(String message, Throwable cause) {
		super(message, cause);
	}
}