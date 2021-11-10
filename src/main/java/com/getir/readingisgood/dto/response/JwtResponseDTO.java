package com.getir.readingisgood.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getir.readingisgood.contants.AppContants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class JwtResponseDTO implements Serializable {
        private String token;
        private String type = AppContants.BEARER;
        @JsonIgnore
        private String id;
        private String username;
        private String email;
        private List<String> roles;
}
