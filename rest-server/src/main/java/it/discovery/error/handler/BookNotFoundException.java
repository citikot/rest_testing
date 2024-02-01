package it.discovery.error.handler;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(int bookId) {
        super(STR."book with id: \{bookId} not found");
    }
}
