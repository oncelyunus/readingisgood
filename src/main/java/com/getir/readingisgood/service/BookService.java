package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.request.BookRequestDTO;
import com.getir.readingisgood.models.Book;
import com.getir.readingisgood.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class BookService {
        private final BookRepository bookRepository;
        private final MongoTemplate mongoTemplate;
        private final RedisTemplate redisTemplate;

        public Book findByIsbn(String isbn) {
                return Optional.ofNullable(bookRepository.findByIsbn(isbn)).orElse(null);
        }

        public void saveBook(Book existBook) {
                bookRepository.save(existBook);
        }

        public void saveOrUpdateBook(BookRequestDTO bookRequestDTO) {
                Book existBook = findByIsbn(bookRequestDTO.getIsbn());
                if(Objects.isNull(existBook)) {
                        log.info("start new Book {} create flow ", bookRequestDTO.getIsbn());
                        existBook = new Book();
                        existBook.setIsbn(bookRequestDTO.getIsbn());
                }

                if(StringUtils.hasText(bookRequestDTO.getTitle())) {
                        existBook.setTitle(bookRequestDTO.getTitle());
                }

                if(Objects.nonNull(bookRequestDTO.getPublished())) {
                        existBook.setPublished(bookRequestDTO.getPublished());
                }

                if(!CollectionUtils.isEmpty(bookRequestDTO.getAuthor())) {
                        existBook.setAuthor(bookRequestDTO.getAuthor());
                }

                if(Objects.nonNull(bookRequestDTO.getAvailable())) {
                        existBook.setAvailable(bookRequestDTO.getAvailable());
                }

                if(Objects.nonNull(bookRequestDTO.getPages())) {
                        existBook.setPages(bookRequestDTO.getPages());
                }

                if(Objects.nonNull(bookRequestDTO.getPrice())) {
                        existBook.setPrice(bookRequestDTO.getPrice());
                }

                if(StringUtils.hasText(bookRequestDTO.getWebsite())) {
                        existBook.setWebsite(bookRequestDTO.getWebsite());
                }

                saveBook(existBook);
                log.info("Book {} saved", bookRequestDTO.getIsbn());
        }

        public Page<Book> listAll(Integer page, Integer size) {
                Pageable pageable = PageRequest.of(page,size);
                var query = new Query().with(pageable);

                Page<Book> results =  PageableExecutionUtils.getPage(
                        mongoTemplate.find(query, Book.class),
                        pageable,
                        () -> mongoTemplate.count(query.skip(0).limit(0), Book.class)
                );
                return results;
        }
}
