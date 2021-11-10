package com.getir.readingisgood.dto.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class CreateBookDTO implements Serializable {

        private String isbn;
        private String author;
        private int count;


}
