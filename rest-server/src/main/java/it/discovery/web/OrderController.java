package it.discovery.web;

import it.discovery.dto.OrderDTO;
import it.discovery.model.Order;
import it.discovery.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final BookRepository bookRepository;

    private final ModelMapper mapper;

    @GetMapping("books/orders")
    public ResponseEntity<List<OrderDTO>> findAll(@Valid PageCriteria pageCriteria) {
        List<Order> orders = bookRepository.findAll().stream().flatMap(book -> book.getOrders().stream()).toList();

        int totalSize = orders.size();
        int nextOffset = (pageCriteria.getPage() + 1) * pageCriteria.getSize();
        List<Order> subList = orders.subList(pageCriteria.getPage() * pageCriteria.getSize(), Math.min(nextOffset, totalSize));

        return ResponseEntity.ok().header("X-TOTAL-COUNT", totalSize + "")
                .body(subList.stream().map(order -> mapper.map(order, OrderDTO.class)).toList());
    }

    @PostMapping("books/{bookId}/orders")
    public void create(@PathVariable int bookId) {
        bookRepository.findById(bookId).addOrder();
    }

    @DeleteMapping("books/{bookId}/orders/{orderId}")
    public void delete(@PathVariable int bookId, @PathVariable String orderId) {
        bookRepository.findById(bookId).cancelOrder(orderId);
    }
}
