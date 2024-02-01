package it.discovery.api;

import it.discovery.dto.OrderDTO;
import it.discovery.web.PageCriteria;

import java.net.URI;
import java.util.List;

public interface OrderClient {

    List<OrderDTO> search(PageCriteria pageCriteria);

    URI create(int bookId);

    void delete(int id);
}
