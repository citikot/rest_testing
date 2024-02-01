package it.discovery.repository;

import it.discovery.event.BookAddedEvent;
import it.discovery.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class SimpleBookRepository implements BookRepository {
    private final Map<Integer, Book> books = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger();

    private final ApplicationEventPublisher publisher;

    @Override
    public Book findById(int id) {
        return books.get(id);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            int id = counter.incrementAndGet();
            book.setId(id);
            books.put(id, book);

            publisher.publishEvent(new BookAddedEvent(book));
            System.out.println("*** Book with id=" + book.getId() + " was created");
        } else {
            books.put(book.getId(), book);
            System.out.println("*** Book with id=" + book.getId() + " was updated");
        }

        return book;
    }

    @Override
    public boolean delete(int id) {
        if (!books.containsKey(id)) {
            return false;
        }

        books.remove(id);
        System.out.println("*** Book with id=" + id + " was deleted");
        return true;
    }

}
