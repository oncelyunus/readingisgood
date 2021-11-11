package com.getir.readingisgood.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Address implements Serializable {
        private String street;
        private String city;
        private String phone;
}
