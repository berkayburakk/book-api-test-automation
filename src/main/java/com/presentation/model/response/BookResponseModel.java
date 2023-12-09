package com.presentation.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BookResponseModel {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("Barcode")
    private String barcode;
    @JsonProperty("Author")
    private String author;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("BookName")
    private String bookName;
}
