package com.getir.readingisgood.helper;

import lombok.Data;

@Data
public class GetirException extends Exception {

        private String code;

        public GetirException(String code, String message) {
                super(message);
                this.code = code;
        }

        public GetirException(String message, Throwable cause) {
                super(message, cause);
        }

        public GetirException(Throwable cause) {
                super(cause);
        }
}
