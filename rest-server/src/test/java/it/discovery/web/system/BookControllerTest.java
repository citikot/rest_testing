package it.discovery.web.system;

import it.discovery.api.BookClient;
import it.discovery.api.impl.Spring6BookClient;
import it.discovery.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebClient(registerRestTemplate = true)
public class BookControllerTest {

//    @Autowired
//    RestTemplate restTemplate;

    @LocalServerPort
    String localServerPort;

    BookClient bookClient;

    @BeforeEach
    void setup() {
        bookClient = new Spring6BookClient(STR."http://localhost:\{localServerPort}/api/books");
    }

    @Test
    @DisplayName("GET /api/books Returns single book at startup")
    void findAll_singleBookPresent_bookReturned() throws Exception {
        List<BookDTO> books = bookClient.findAll();
        assertEquals(1, books.size());
        assertEquals("REST API", books.get(0).getTitle());
    }

    @Test
    @DisplayName("POST /api/books Creates new book without required fields")
    void save_titleIsEmpty_badRequest() throws Exception {
        BookDTO book = new BookDTO();
        book.setAuthor("Roy Fielding");
        book.setYear(2022);
        book.setAmount(5);

        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () ->
                bookClient.create(book));
        assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getStatusCode().value());
        //TODO check that error message contains title property
    }
}

