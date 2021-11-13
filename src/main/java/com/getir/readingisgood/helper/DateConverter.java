package com.getir.readingisgood.helper;

import org.springframework.util.StringUtils;

import java.util.Map;

public class DateConverter {

        private static Map<Integer, String> months = Map.ofEntries(
                Map.entry(1,"January"),
                Map.entry(2,"February"),
                Map.entry(3,"March"),
                Map.entry(4, "April"),
                Map.entry(5, "May"),
                Map.entry(6, "June"),
                Map.entry(7, "July"),
                Map.entry(8, "August"),
                Map.entry(9, "September"),
                Map.entry(10, "October"),
                Map.entry(11, "November"),
                Map.entry(12, "December")
        );

        public static String getMonth(int month) {
                String m =  months.get(month);
                if(StringUtils.hasText(m)) {
                        return m;
                }

                return "UNKNOWN";
        }
}
