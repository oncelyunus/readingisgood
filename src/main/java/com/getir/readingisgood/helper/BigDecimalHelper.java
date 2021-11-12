package com.getir.readingisgood.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalHelper {

        public static BigDecimal toPrecision(BigDecimal number, int precision) {
                return number.setScale(precision, RoundingMode.HALF_UP);
        }
}
