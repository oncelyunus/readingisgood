package com.getir.readingisgood.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@JsonPropertyOrder({ "month", "totalOrderCount", "totalBookCount", "totalPurchasedAmount"})
public class StatResponseDTO implements Serializable {
        private String month;
        private Integer totalOrderCount;
        private Integer totalBookCount;
        private BigDecimal totalPurchasedAmount;
}
