package com.getir.readingisgood.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookRequestDTO implements Serializable {
        @NotNull
        private String isbn;
        private String title;
        private String subtitle;
        private Set<String> author;
        private Date published;
        private String description;
        private String website;
        private Integer available = 0;
        private Integer pages = 0;
        private BigDecimal price;
}
