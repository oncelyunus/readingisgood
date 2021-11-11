package com.getir.readingisgood.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Accessors(chain = true)
@Document(collection = "books")
public class Book {

        @Id
        @JsonIgnore
        private String id;

        @Indexed(unique = true)
        private String isbn;

        private String title;
        private Set<String> author;
        private String publishedDate;
        private int available;
        private int pages;
        private String language;
        private BigDecimal price;
}
