package it.discovery.api.client;

import it.discovery.dto.BookDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;
import java.util.List;

@HttpExchange("books")
public interface BookClient {

    @GetExchange
    List<BookDTO> findAll();

    @GetExchange("{id}")
    ResponseEntity<BookDTO> findById(@PathVariable int id);

    @PostExchange
    URI create(@RequestBody BookDTO book);

    @DeleteExchange("{id}")
    void delete(@PathVariable int id);

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8080/api/"));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        BookClient client = proxyFactory.createClient(BookClient.class);
        List<BookDTO> list = client.findAll();
        System.out.println(list);
    }
}
