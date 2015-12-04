package ua.softserve.booklibrary.exception;

public class AlreadyExistException extends Exception {

    private static final long serialVersionUID = -8202369306420404105L;

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException() {
        super("Entity is already exist");
    }

}
