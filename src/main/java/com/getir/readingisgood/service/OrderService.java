package com.getir.readingisgood.service;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import com.getir.readingisgood.contants.ErrorCodes;
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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

        public void buy(String username, String orderId) throws GetirException {
                Order order = orderRepository.findByCustomerAndStatusAndId(username, Status.OPEN, orderId);
                if(Objects.isNull(order)) {
                        throw new GetirException(ErrorCodes.ORDER_NOT_AVALIABLE_CODE,
                                ErrorCodes.ORDER_NOT_AVALIABLE_MESSAGE);
                }

                order.setStatus(Status.COMPLETED);
                orderRepository.save(order);
        }

        public Order addCart(String userName, String bookId) throws GetirException {
                Order order = orderRepository.findByCustomerAndStatus(userName, Status.OPEN);
                if(Objects.isNull(order)) {
                        order = new Order().setCustomer(userName)
                                .setStatus(Status.OPEN)
                                .setDate(new Date())
                                .setPrice(new BigDecimal(0));
                }

                checkBookAvaliable(bookId);
                Book existBook = bookRepository.findByID(bookId);
                BigDecimal totalPrice = order.getPrice().add(existBook.getPrice());
                order.setPrice(BigDecimalHelper.toPrecision(totalPrice, 2));
                order.getBooks().add(existBook);
                return mongoOperations.save(order);
        }

        private synchronized void checkBookAvaliable(String bookId) throws GetirException {
                UpdateResult result =  mongoOperations.update(Book.class)
                        .matching(Query.query(where("id").is(bookId).and("available").gt(0)))
                        .apply(new Update().inc("available", -1))
                                .first();

                if (result.getModifiedCount() != 1) {
                        throw new GetirException(ErrorCodes.BOOK_NOT_AVALIABLE_CODE,
                                ErrorCodes.BOOK_NOT_AVALIABLE_MESSAGE);
                }
        }

        public Order findById(String name, String id) throws GetirException {
                Order order = orderRepository.findByCustomerAndId(name, id);
                if(Objects.isNull(order)) {
                        throw new GetirException(ErrorCodes.ORDER_NOT_AVALIABLE_CODE,
                                ErrorCodes.ORDER_NOT_AVALIABLE_MESSAGE);
                }

                return order;
        }

        public List<Order> fetchOrderBetweenDate(Date fromDate, Date toDate) {
                Criteria publishedDateCriteria = Criteria
                        .where("date").gte(fromDate);
                if(Objects.nonNull(toDate)) {
                        publishedDateCriteria.lte(toDate);
                }
                Query query = new Query(publishedDateCriteria);
                return  mongoOperations.find(query,Order.class);
        }
}
