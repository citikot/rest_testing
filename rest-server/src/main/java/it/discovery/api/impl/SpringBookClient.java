package it.discovery.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.discovery.api.BookClient;
import it.discovery.dto.BookDTO;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpringBookClient implements BookClient {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public SpringBookClient(String bookUrl) {
        restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(bookUrl));
        objectMapper = new ObjectMapper().findAndRegisterModules();
    }

    @Override
    public List<BookDTO> findAll() {
        List<Map<String, ?>> items = restTemplate.getForObject("", List.class);
        return items.stream().map(item -> objectMapper.convertValue(item, BookDTO.class)).toList();
    }

    @Override
    public Optional<BookDTO> findById(int bookId) {
        return Optional.empty();
    }

    @Override
    public URI create(BookDTO book) {
        return restTemplate.postForLocation("", book);
    }

    @Override
    public void delete(int bookId) {

    }

    public static void main(String[] args) {
        BookClient bookClient = new SpringBookClient("http://localhost:8080/api/books");
        List<BookDTO> list = bookClient.findAll();
        System.out.println(list);
    }
}
