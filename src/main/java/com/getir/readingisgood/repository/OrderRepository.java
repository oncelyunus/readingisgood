package com.getir.readingisgood.repository;

import com.getir.readingisgood.enums.Status;
import com.getir.readingisgood.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

        Order findByCustomerAndId(String customer, String id);

        Order findByCustomerAndStatus(String customer, Status status);
        Order findByCustomerAndStatusAndId(String customer, Status status, String id);

        @Query("{'date' :{'$gte':?0, '$lt':?1}}")
        public List<Order> findByDate( Date from,Date to);
}
