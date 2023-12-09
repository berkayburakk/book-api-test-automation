package com.presentation.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorModel {
    @JsonProperty("error")
    private String error;
}
