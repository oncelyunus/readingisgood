package com.getir.readingisgood.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ResultDTO implements Serializable {

        @ApiModelProperty(value = "Error code", notes = "sample: 0: success, others: error", example = "0", dataType = "java.lang.String")
        private String code;

        @ApiModelProperty(value = "Friendly message", example = "user friendly message")
        private String friendlyMessage;

        @ApiModelProperty(value = "Error message", example = "technical error detail")
        private String message;
}
