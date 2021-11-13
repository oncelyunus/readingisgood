package com.getir.readingisgood.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.models.Book;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class ReadyEvent {
        private final ObjectMapper objectMapper;
        private final BookRepository bookRepository;
        private final OrderRepository orderRepository;
        private final ResourceLoader resourceLoader;

        @EventListener(ApplicationReadyEvent.class)
        public void afterStartup() {
                log.info("loading required things");
                try {
                        List<Book> bookList = objectMapper.readValue(
                                resourceLoader.getResource("classpath:book.json").getInputStream(),
                                new TypeReference<>() {});
                        bookRepository.deleteAll();
                        // orderRepository.deleteAll();

                        bookRepository.saveAll(bookList);
                } catch (IOException e) {
                        log.error(e.getMessage(), e);
                }
                log.info("Books are ready");
        }
}
