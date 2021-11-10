package com.getir.readingisgood.dto.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserAuthToken extends UsernamePasswordAuthenticationToken {

        private UserAuthToken(final String userId, final String password) {
                super(userId, password);
        }

        public static UserAuthToken of(final String userId, final String password) {
                return new UserAuthToken(userId, password);
        }

        public String getUserId() {
                return String.valueOf(this.getPrincipal());
        }
}
