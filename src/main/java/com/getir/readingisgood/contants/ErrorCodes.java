package com.getir.readingisgood.contants;

public class ErrorCodes {
        public static final String ORDER_NOT_AVALIABLE_CODE = "gt680";
        public static final String ORDER_NOT_AVALIABLE_MESSAGE = "Order not found";

        private ErrorCodes() { }

        public static final String SYSTEM_ERROR = "-1";

        public static final String USER_ALREADY_EXISTS_MESSAGE = "User already exists: ";
        public static final String USER_ALREADY_EXISTS_CODE = "gt481";

        public static final String NON_NEGATIVE_MESSAGE = "%s must be positive or zero";
        public static final String NON_NEGATIVE_CODE = "gt482";

        public static final String NULL_CHECK_MESSAGE = "%s must not be null";
        public static final String NULL_CHECK_CODE = "gt483";

        public static final String BOOK_NOT_AVALIABLE_MESSAGE = "book is not in store";
        public static final String BOOK_NOT_AVALIABLE_CODE = "gt580";

}
