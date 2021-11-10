package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.request.CreateBookDTO;
import com.getir.readingisgood.dto.request.UpdateBookDTO;
import com.getir.readingisgood.dto.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/book",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
public class BookController {

        @PostMapping("/create")
        public ResponseEntity<?> createBook(@RequestBody CreateBookDTO createBookDTO) {
                return ResponseEntity.ok().body(new ResponseDTO().setMessage(createBookDTO.toString()));
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> updateBook(@PathVariable String id,
                @RequestBody UpdateBookDTO updateBookDTO) {
                return ResponseEntity.ok().body(new ResponseDTO().setMessage(updateBookDTO.toString()));
        }

}
