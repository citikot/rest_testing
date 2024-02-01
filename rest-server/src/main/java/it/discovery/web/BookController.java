package it.discovery.web;

import it.discovery.dto.BookDTO;
import it.discovery.error.handler.BookNotFoundException;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    private final ModelMapper mapper;

//    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Book sampleBook() {
//        return new Book();
//    }

    @GetMapping
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(book -> mapper.map(book, BookDTO.class)).toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> findById(@PathVariable int id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return Optional.ofNullable(bookRepository.findById(id))
                .map(ResponseEntity::ok).orElseThrow(() -> new BookNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody BookDTO book) {
        Book book1 = bookRepository.save(mapper.map(book, Book.class));

        return ResponseEntity.created(URI.create(STR."/api/books/\{book1.getId()}")).build();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody BookDTO book) {
        bookRepository.save(mapper.map(book, Book.class));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        bookRepository.delete(id);
    }
}
