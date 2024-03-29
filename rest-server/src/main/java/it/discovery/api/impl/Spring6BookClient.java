package it.discovery.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.api.BookClient;
import it.discovery.dto.BookDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class Spring6BookClient implements BookClient {

    private final ObjectMapper objectMapper;

    private final RestClient restClient;

    public Spring6BookClient(String bookUrl) {
        objectMapper = new ObjectMapper().findAndRegisterModules();
        restClient = RestClient.create(bookUrl);
    }

    @Override
    public List<BookDTO> findAll() {
        return restClient.get().retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Optional<BookDTO> findById(int bookId) {
        return Optional.empty();
    }

    @Override
    public URI create(BookDTO book) {
        ResponseEntity<Void> response = restClient.post().body(book)
                .contentType(MediaType.APPLICATION_JSON).retrieve().toEntity(Void.class);
        return response.getHeaders().getLocation();
    }

    @Override
    public void delete(int bookId) {

    }

}
