package com.getir.readingisgood.controller;

import com.getir.readingisgood.contants.Messages;
import com.getir.readingisgood.dto.request.OrderBuyDTO;
import com.getir.readingisgood.dto.response.ResponseDTO;
import com.getir.readingisgood.helper.GetirException;
import com.getir.readingisgood.models.Order;
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/order",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
public class OrderController {

        private final OrderService orderService;

        @PostMapping("/buy/{order}")
        public ResponseEntity<?> buy(Authentication authentication,
                @PathVariable String order) throws GetirException {
                Order completed = orderService.buy(authentication.getName(), order);
                return ResponseEntity.ok().body(new ResponseDTO().setMessage(
                        String.format(Messages.ORDER_COMPLETED_MESSAGE, completed.getId())
                ));
        }

        @PostMapping("/addCart")
        public ResponseEntity<?> toCart(Authentication authentication,
                @Valid @RequestBody OrderBuyDTO orderBuyDTO) throws GetirException {
                log.info("current username {} wants to add to cart {}", authentication.getName(),
                        orderBuyDTO.getIsbn());
                Order order = orderService.addCart(authentication.getName(),
                        orderBuyDTO.getIsbn());
                return ResponseEntity.ok().body(new ResponseDTO()
                        .setMessage(
                                String.format(Messages.ORDER_ADD2CART_MESSAGE, orderBuyDTO.getIsbn(), order.getId())
                        ));
        }

        @PostMapping("/{id}")
        public ResponseEntity<?> findById(Authentication authentication,
                @PathVariable String id) throws GetirException {
                log.info("current username {}", authentication.getName());
                Order order = orderService.findById(authentication.getName(), id);
                return ResponseEntity.ok().body(order);
        }

        @PostMapping("/fetch")
        public ResponseEntity<?> fetchResult(@RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                pattern="yyyy-MM-dd") Date fromDate,
                @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,
                        pattern="yyyy-MM-dd") Date toDate) {
                List<Order> orderList = orderService.fetchOrderBetweenDate(fromDate, toDate);
                return ResponseEntity.ok().body(orderList);
        }

}
