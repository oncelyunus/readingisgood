package com.getir.readingisgood.validation;

import com.getir.readingisgood.contants.ErrorCodes;
import com.getir.readingisgood.dto.request.BookRequestDTO;
import com.getir.readingisgood.helper.GetirException;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class BookValidator {

        public static void validator(BookRequestDTO bookRequestDTO) throws GetirException {
                if(!StringUtils.hasText(bookRequestDTO.getIsbn())) {
                        throw new GetirException(ErrorCodes.NULL_CHECK_CODE,
                                String.format(ErrorCodes.NULL_CHECK_MESSAGE, "isbn"));
                }
                if(bookRequestDTO.getAvailable()<0){
                        throw new GetirException(ErrorCodes.NON_NEGATIVE_CODE,
                                String.format(ErrorCodes.NON_NEGATIVE_MESSAGE, "avaliable"));
                }

                if(bookRequestDTO.getPages()<0){
                        throw new GetirException(ErrorCodes.NON_NEGATIVE_CODE,
                                String.format(ErrorCodes.NON_NEGATIVE_MESSAGE, "pages"));
                }

                if(Objects.nonNull(bookRequestDTO.getPrice()) &&
                        bookRequestDTO.getPrice().intValue() <0 ){
                        throw new GetirException(ErrorCodes.NON_NEGATIVE_CODE,
                                String.format(ErrorCodes.NON_NEGATIVE_MESSAGE, "price"));
                }
        }
}
