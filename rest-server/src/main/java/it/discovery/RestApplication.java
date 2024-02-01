package it.discovery;

import it.discovery.model.Book;
import it.discovery.repository.BookRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(BookRepository bookRepository) {
        return args -> {
            Book book = new Book();
            book.setName("REST API");
            book.setAuthor("Roy Fielding");
            book.setYear(2022);
            book.setAmount(5);
            bookRepository.save(book);
        };
    }

}
