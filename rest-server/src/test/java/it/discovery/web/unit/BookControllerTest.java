package it.discovery.web.unit;

import it.discovery.dto.BookDTO;
import it.discovery.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JacksonTester<BookDTO> jacksonTester;

    @MockBean
    BookRepository bookRepository;

    @Test
    @DisplayName("GET /api/books Returns single book at startup")
    void findAll_noBooksPresent_emptyArrayReturned() throws Exception {
        BDDMockito.given(bookRepository.findAll()).willReturn(List.of());

        mockMvc.perform(get("/api/books")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("POST /api/books Creates new book without required fields")
    void save_titleIsEmpty_badRequest() throws Exception {
        BookDTO book = new BookDTO();
        book.setAuthor("Roy Fielding");
        book.setYear(2022);
        book.setAmount(5);

        mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(book).getJson())).andExpect(status().isBadRequest());

        //TODO change RestApplication to avoid creating new book on startup
        //verify(bookRepository, never()).save(any(Book.class));
    }

}

