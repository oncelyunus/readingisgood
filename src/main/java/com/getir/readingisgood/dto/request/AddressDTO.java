package com.getir.readingisgood.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDTO implements Serializable {
        private String street;
        private String city;
        private String phone;
}
