package com.getir.readingisgood.service;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import com.getir.readingisgood.contants.Messages;
import com.getir.readingisgood.enums.Status;
import com.getir.readingisgood.helper.BigDecimalHelper;
import com.getir.readingisgood.helper.GetirException;
import com.getir.readingisgood.models.Book;
import com.getir.readingisgood.models.Order;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderService {

        private final MongoOperations mongoOperations;
        private final OrderRepository orderRepository;
        private final BookRepository bookRepository;

        public Order buy(String username, String orderId) throws GetirException {
                Order order = orderRepository.findByCustomerAndStatusAndId(username, Status.OPEN, orderId);
                if(Objects.isNull(order)) {
                        throw new GetirException(Messages.ORDER_NOT_AVALIABLE_CODE,
                                Messages.ORDER_NOT_AVALIABLE_MESSAGE);
                }

                order.setStatus(Status.COMPLETED);
                return orderRepository.save(order);
        }

        public Order addCart(String userName, String isbn) throws GetirException {
                Order order = orderRepository.findByCustomerAndStatus(userName, Status.OPEN);
                if(Objects.isNull(order)) {
                        order = new Order().setCustomer(userName)
                                .setStatus(Status.OPEN)
                                .setDate(new Date())
                                .setPrice(new BigDecimal(0));
                }

                checkBookAvaliable(isbn);
                Book existBook = bookRepository.findByIsbn(isbn);
                BigDecimal totalPrice = order.getPrice().add(existBook.getPrice());
                order.setPrice(BigDecimalHelper.toPrecision(totalPrice, 2));
                order.getBooks().add(existBook);
                order.setItemCount(order.getBooks().size());
                return mongoOperations.save(order);
        }

        private synchronized void checkBookAvaliable(String isbn) throws GetirException {
                UpdateResult result =  mongoOperations.update(Book.class)
                        .matching(Query.query(where("isbn").is(isbn).and("available").gt(0)))
                        .apply(new Update().inc("available", -1))
                                .first();

                if (result.getModifiedCount() != 1) {
                        throw new GetirException(Messages.BOOK_NOT_AVALIABLE_CODE,
                                Messages.BOOK_NOT_AVALIABLE_MESSAGE);
                }
        }

        public Order findById(String name, String id) throws GetirException {
                Order order = orderRepository.findByCustomerAndId(name, id);
                if(Objects.isNull(order)) {
                        throw new GetirException(Messages.ORDER_NOT_AVALIABLE_CODE,
                                Messages.ORDER_NOT_AVALIABLE_MESSAGE);
                }

                return order;
        }

        public List<Order> fetchOrderBetweenDate(Date fromDate, Date toDate) {
                Criteria dateCriteria = Criteria
                        .where("date").gte(fromDate);
                if(Objects.nonNull(toDate)) {
                        dateCriteria.lte(toDate);
                }
                var query = new Query(dateCriteria);
                return  mongoOperations.find(query,Order.class);
        }

        public Page<Order> getMyOrder(String customer, Integer page, Integer size) {
                Criteria criteria = Criteria
                        .where("customer").gte(customer);
                Pageable pageable = PageRequest.of(page,size);
                var query = new Query().with(pageable);
                query.addCriteria(criteria);

                Page<Order> results =  PageableExecutionUtils.getPage(
                        mongoOperations.find(query, Order.class),
                        pageable,
                        () -> mongoOperations.count(query.skip(0).limit(0), Order.class)
                );
                return results;
        }
}
