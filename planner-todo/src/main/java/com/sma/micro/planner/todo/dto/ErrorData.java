package com.sma.micro.planner.todo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorData {
    private final String errorCode;
    private final String errorName;
    private final String errorDesc;

    @JsonCreator
    public ErrorData(@JsonProperty("errorCode") String errorCode,
                     @JsonProperty("errorName") String errorName,
                     @JsonProperty("errorDesc") String errorDesc) {
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorDesc = errorDesc;
    }

}
