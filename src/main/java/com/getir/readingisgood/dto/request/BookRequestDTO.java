package com.getir.readingisgood.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
public class BookRequestDTO implements Serializable {

        private String title;
        @NotNull
        private String isbn;
        private Set<String> author;
        private String publishedDate;

        private Integer available = 0;

        private Integer pages = 0;

        private String language;

        private BigDecimal price;


}
