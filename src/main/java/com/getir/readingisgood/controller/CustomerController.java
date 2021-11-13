package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.response.ResponseDTO;
import com.getir.readingisgood.helper.GetirException;
import com.getir.readingisgood.models.Order;
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/customer",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
public class CustomerController {

        private final OrderService orderService;

        @GetMapping("/orders")
        public ResponseEntity<?> findById(Authentication authentication,
                @RequestParam(required = false, defaultValue = "0") Integer page,
                @RequestParam(required = false, defaultValue = "50") Integer size)  {
                log.info("current username {}", authentication.getName());
                Page<Order> orderList = orderService.getMyOrder(authentication.getName(), page,size);
                return ResponseEntity.ok().body(new ResponseDTO().setData(orderList));
        }
}
