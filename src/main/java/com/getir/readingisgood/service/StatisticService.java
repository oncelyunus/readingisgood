package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.response.StatResponseDTO;
import com.getir.readingisgood.enums.Status;
import com.getir.readingisgood.helper.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.bson.types.Decimal128;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@Service
@Log4j2
@RequiredArgsConstructor
public class StatisticService {

        private final MongoOperations mongoOperations;

        public StatResponseDTO statistic() {
                MatchOperation matchStage = Aggregation.match(new Criteria("status").is(Status.COMPLETED));

                ProjectionOperation projectStage = Aggregation.project("date","item_count","price")
                        .and(DateOperators.Month.monthOf("date")).as("month");
                GroupOperation groupByStateAndSumPop = group("month")
                        .count().as("order_count")
                        .sum("item_count").as("book_count")
                        .sum("price").as("amount");
                Aggregation aggregation
                        = Aggregation.newAggregation(matchStage, projectStage,groupByStateAndSumPop);

                AggregationResults<Document> aggResults = mongoOperations.aggregate(aggregation,
                        "orders", Document.class);

                Document document =  aggResults.getUniqueMappedResult();
                if(Objects.nonNull(document) &&
                        !document.isEmpty()) {
                        return new StatResponseDTO()
                                .setMonth(DateConverter.getMonth(document.getInteger("_id")))
                                .setTotalBookCount(document.getInteger("book_count"))
                                .setTotalOrderCount(document.getInteger("order_count"))
                                .setTotalPurchasedAmount(document.get("amount", Decimal128.class).bigDecimalValue());
                }

                return new StatResponseDTO();
        }
}
