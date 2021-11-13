package com.getir.readingisgood.repository;

import com.getir.readingisgood.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

        @Query(value="{ 'isbn' : ?0 }",fields="{ 'id' : 1, 'title' : 1,'price' : 1}")
        Book findByIsbn(String isbn);


}
