package it.discovery.advice;

import it.discovery.dto.BookDTO;
import it.discovery.dto.OrderDTO;
import it.discovery.model.Book;
import it.discovery.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@RequiredArgsConstructor
public class EntityConverterResponseAdvice implements ResponseBodyAdvice {

    private final ModelMapper mapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //TODO support List and generic types: List<Book>
        //hint: use returnType
        return switch (body) {
            case Book book -> mapper.map(book, BookDTO.class);
            case Order order -> mapper.map(order, OrderDTO.class);
            case null, default -> body;
        };
    }
}
