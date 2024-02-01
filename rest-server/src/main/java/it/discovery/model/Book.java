package it.discovery.model;

import it.discovery.api.BookClient;
import it.discovery.api.impl.Spring6BookClient;
import it.discovery.dto.BookDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Book {
    private int id;

    private String author;

    private String name;

    private int year;

    private int amount;

    private List<Order> orders;

    public Book() {
        orders = new ArrayList<>();
    }

    public void addOrder() {
        if (amount <= 0) {
            throw new IllegalStateException("No books in the shop");
        }

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setCreatedAt(LocalDateTime.now());
        order.setBook(this);
        orders.add(order);
        amount--;

        System.out.println("Order " + order + "was created");
    }

    public boolean cancelOrder(String uuid) {
        boolean found = orders.removeIf(order -> !order.isCompleted() &&
                order.getId().equals(UUID.fromString(uuid)));
        if (found) {
            System.out.println("Order " + uuid + "was cancelled");
            amount++;
        }
        return found;
    }

    public int getTotalOrderCount() {
        return orders.size();
    }

    public static void main(String[] args) {
//        BookClient bookClient = new SpringBookClient("http://localhost:8080/api/books");
        BookClient bookClient = new Spring6BookClient("http://localhost:8080/api/books");
        List<BookDTO> list = bookClient.findAll();
        System.out.println(list);
    }

}
