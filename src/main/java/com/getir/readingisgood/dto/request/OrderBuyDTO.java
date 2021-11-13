package com.getir.readingisgood.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class OrderBuyDTO implements Serializable {
        private String isbn;
}
