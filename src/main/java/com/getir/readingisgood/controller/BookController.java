package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.request.BookRequestDTO;
import com.getir.readingisgood.dto.response.ResponseDTO;
import com.getir.readingisgood.helper.GetirException;
import com.getir.readingisgood.service.BookService;
import com.getir.readingisgood.validation.BookValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/book",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
public class BookController {

        private final BookService bookService;

        @PostMapping("/createOrUpdate")
        public ResponseEntity<?> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) throws GetirException {
                BookValidator.validator(bookRequestDTO);
                bookService.saveOrUpdateBook(bookRequestDTO);
                return ResponseEntity.ok().body(new ResponseDTO().setMessage("Book Saved"));
        }

        @GetMapping("/list")
        public ResponseEntity<?> listBooks(@RequestParam(required = false, defaultValue = "0") Integer page,
                @RequestParam(required = false, defaultValue = "50") Integer size) {
                return ResponseEntity.ok(bookService.listAll(page, size));
        }



}
