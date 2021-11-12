package com.getir.readingisgood.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@Log4j2
@RequiredArgsConstructor
public class StatisticService {

        private final MongoOperations mongoOperations;

        public Document statistic() {
                Aggregation agg = newAggregation(
                        Aggregation.project("date", "status")
                                .and(DateOperators.Month.monthOf("date")).as("month"),
                        Aggregation.group("month", "status")
                                .count().as("count"),
                        Aggregation.group("_id.month")
                                .push(new Document("status", "$_id.status").append("count", "$count"))
                                .as("status_counts"),
                        Aggregation.project("status_counts").and("month").previousOperation()
                );

                AggregationResults<Document> aggResults = mongoOperations.aggregate(agg, "orders", Document.class);
                return aggResults.getRawResults();
        }
}
