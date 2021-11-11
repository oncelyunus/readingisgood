package com.getir.readingisgood.helper;

import java.math.BigDecimal;

public class BigDecimalHelper {

        private static BigDecimal toPrecision(BigDecimal dec, int precision) {
                String plain = dec.movePointRight(precision).toPlainString();
                return new BigDecimal(plain.substring(0, plain.indexOf("."))).movePointLeft(precision);
        }
}
