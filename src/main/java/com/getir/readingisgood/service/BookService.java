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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class BookService {
        private final BookRepository bookRepository;
        private final MongoTemplate mongoTemplate;
        private final RedisTemplate redisTemplate;

        public Boolean isExists(String isbn) {
                return !Objects.isNull(findByIsbn(isbn));
        }

        public Book findByIsbn(String isbn) {
                return bookRepository.findByIsbn(isbn).orElse(null);
        }

        public Book saveBook(Book existBook) {
                return bookRepository.save(existBook);
        }

        public void saveOrUpdateBook(BookRequestDTO bookRequestDTO) {
                Book existBook = findByIsbn(bookRequestDTO.getIsbn().toString());
                if(Objects.isNull(existBook)) {
                        log.info("start new Book {} create flow ", bookRequestDTO.getIsbn());
                        existBook = new Book();
                        existBook.setIsbn(bookRequestDTO.getIsbn().toString());
                }

                if(StringUtils.hasText(bookRequestDTO.getTitle())) {
                        existBook.setTitle(bookRequestDTO.getTitle());
                }

                if(StringUtils.hasText(bookRequestDTO.getLanguage())) {
                        existBook.setLanguage(bookRequestDTO.getLanguage());
                }

                if(StringUtils.hasText(bookRequestDTO.getPublishedDate())) {
                        existBook.setPublishedDate(bookRequestDTO.getPublishedDate());
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
