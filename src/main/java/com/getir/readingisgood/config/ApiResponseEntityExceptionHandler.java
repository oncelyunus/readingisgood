package com.getir.readingisgood.config;

import com.getir.readingisgood.dto.response.ResultDTO;
import com.getir.readingisgood.helper.GetirException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@ControllerAdvice
public class ApiResponseEntityExceptionHandler {

        @ExceptionHandler(GetirException.class)
        public ResponseEntity<?> handleAccessDenied(final GetirException ex,
                final WebRequest request) {
                log.error(ex.getMessage(), ex);
                final ResultDTO result = new ResultDTO();
                result.setCode(ex.getCode());
                result.setMessage(ex.getMessage());
                result.setFriendlyMessage("Oopps!");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleUnhandledExceptions(final Exception ex, final WebRequest request) {
                log.error(ex.getMessage(), ex);
                final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

                final ResultDTO result = new ResultDTO();
                result.setCode(String.valueOf(httpStatus.series()));
                result.setMessage(ex.getMessage());
                result.setFriendlyMessage("System error! Trace with log file!");

                return ResponseEntity.status(httpStatus).body(result);
        }
}
