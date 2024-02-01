package it.discovery.api;

import it.discovery.dto.BookDTO;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface BookClient {

    List<BookDTO> findAll();

    Optional<BookDTO> findById(int bookId);

    URI create(BookDTO book);

    void delete(int bookId);
}
