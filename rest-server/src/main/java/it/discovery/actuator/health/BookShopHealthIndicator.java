package it.discovery.actuator.health;

import it.discovery.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookShopHealthIndicator extends AbstractHealthIndicator {

    private final static int DEFAULT_BOOK_AMOUNT_THRESHOLD = 20;

    private final BookRepository bookRepository;

    private final int bookAmountThreshold;

    public BookShopHealthIndicator(BookRepository bookRepository, Environment env) {
        this.bookRepository = bookRepository;
        bookAmountThreshold = env.getProperty("book.amount.threshold", Integer.class, DEFAULT_BOOK_AMOUNT_THRESHOLD);
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            int size = bookRepository.findAll().size();
            if (size > bookAmountThreshold) {
                builder.up().withDetail("Book amount", size);
            } else if (size > 0) {
                builder.down().withDetail("Book shortage", size);
            } else {
                builder.outOfService().withDetail("Emergency situation", "Book shop is empty");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            builder.unknown().withException(ex);
        }
    }
}
