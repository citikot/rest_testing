package it.discovery.web.restassured;

import io.restassured.http.ContentType;
import it.discovery.dto.BookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

//    @BeforeEach
//    void setup() {
//        RestAssuredMockMvc.mockMvc(mockMvc);
//    }

    @Test
    @DisplayName("GET /api/books Returns single book at startup")
    void findAll_singleBookPresent_bookReturned() {
        Integer bookCount = given().mockMvc(mockMvc).when().get("/api/books").then().status(HttpStatus.OK)
                .contentType(ContentType.JSON)
                .extract().response().jsonPath().getObject("size()", Integer.class);
        assertEquals(1, bookCount);
    }

    @Test
    @DisplayName("POST /api/books Creates new book without required fields")
    void save_titleIsEmpty_badRequest() {
        BookDTO book = new BookDTO();
        book.setAuthor("Roy Fielding");
        book.setYear(2022);
        book.setAmount(5);

        given().mockMvc(mockMvc).body(book).contentType(ContentType.JSON)
                .when().post("/api/books")
                .then().status(HttpStatus.BAD_REQUEST);
    }

}

