package it.discovery.config;

import it.discovery.dto.BookDTO;
import it.discovery.dto.OrderDTO;
import it.discovery.model.Book;
import it.discovery.model.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private List<HandlerInterceptor> interceptors;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api/", HandlerTypePredicate.forAnnotation(RestController.class));
    }

    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        TypeMap<Book, BookDTO> bookMap = mapper.createTypeMap(Book.class, BookDTO.class);
        bookMap.addMapping(Book::getName, BookDTO::setTitle);

        TypeMap<BookDTO, Book> bookMap2 = mapper.createTypeMap(BookDTO.class, Book.class);
        bookMap2.addMapping(BookDTO::getTitle, Book::setName);

        TypeMap<Order, OrderDTO> orderMap = mapper.createTypeMap(Order.class, OrderDTO.class);
        orderMap.addMapping(order -> order.getBook().getName(), OrderDTO::setTitle);
        orderMap.addMapping(order -> order.getBook().getId(), OrderDTO::setBookId);
        return mapper;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.forEach(registry::addInterceptor);
    }
}
