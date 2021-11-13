package com.getir.readingisgood.models;

import com.getir.readingisgood.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@Document(collection = "orders")
public class Order {

        @Id
        private String id;

        @Field("customer")
        private String customer;

        @Field("date")
        private Date date;

        @Field("status")
        private Status status = Status.OPEN;

        @DBRef
        private List<Book> books = new ArrayList<>();

        @Field(value = "price", targetType = FieldType.DECIMAL128)
        private BigDecimal price;

        @Field("item_count")
        private Integer itemCount;

}
