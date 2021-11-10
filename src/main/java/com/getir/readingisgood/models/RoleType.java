package com.getir.readingisgood.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
        USER("ROLE_USER"),
        ADMIN("ROLE_ADMIN");

        private final String name;
}
