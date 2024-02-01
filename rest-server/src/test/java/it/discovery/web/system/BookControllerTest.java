package it.discovery.web.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.dto.BookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient(registerRestTemplate = true)
public class BookControllerTest {

    @Autowired
    RestTemplate restTemplate;

    @LocalServerPort
    String localServerPort;

    static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    @Test
    @DisplayName("GET /api/books Returns single book at startup")
    void findAll_singleBookPresent_bookReturned() throws Exception {
        List books = restTemplate.getForObject(STR."http://localhost:\{localServerPort}/api/books", List.class);
        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("POST /api/books Creates new book without required fields")
    void save_titleIsEmpty_badRequest() throws Exception {
        BookDTO book = new BookDTO();
        book.setAuthor("Roy Fielding");
        book.setYear(2022);
        book.setAmount(5);

        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class, () ->
                restTemplate.postForEntity(STR."http://localhost:\{localServerPort}/api/books",
                        book, Void.class));
        assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getStatusCode().value());
        //TODO check that error message contains title property
    }

}

