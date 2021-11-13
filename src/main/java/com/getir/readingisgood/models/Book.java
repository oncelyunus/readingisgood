package com.getir.readingisgood.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
@Document(collection = "books")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

        @Id
        private String id;

        @Indexed(unique = true)
        private String isbn;

        private String title;
        private String subtitle;
        private Set<String> author;
        private Date published;
        private Integer pages;
        private String description;
        private String website;
        @Field("available")
        private Integer available;
        private BigDecimal price;
}
