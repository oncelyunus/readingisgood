package com.getir.readingisgood.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class BookResponseDTO implements Serializable {

        private String isbn;
        private String title;
        private Set<String> author;
        private String publishedDate;
        private int available;
        private int pages;
        private String language;
        private BigDecimal price;
        private boolean hasMore = available>0;
}
