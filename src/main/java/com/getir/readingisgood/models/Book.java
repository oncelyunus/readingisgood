package com.getir.readingisgood.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Accessors(chain = true)
@Document(collection = "books")
public class Book {

        @Id
        private String id;

        @Indexed(unique = true)
        private String isbn;

        private String author;
        private int count;
}
