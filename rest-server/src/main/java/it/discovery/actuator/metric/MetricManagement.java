package it.discovery.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import it.discovery.event.BookAddedEvent;
import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MetricManagement {

    private final MeterRegistry meterRegistry;

    private final BookRepository bookRepository;

    @EventListener
    void onBookAdded(BookAddedEvent event) {
        Counter booksAdded = meterRegistry.counter("books.added",
                "author", event.book().getAuthor());

        booksAdded.increment();
    }

    @PostConstruct
    void initGaugeCounter() {
        Gauge.builder("total.orders", () -> bookRepository.findAll().stream()
                        .mapToInt(Book::getTotalOrderCount).sum())
                .register(meterRegistry);
    }
}
