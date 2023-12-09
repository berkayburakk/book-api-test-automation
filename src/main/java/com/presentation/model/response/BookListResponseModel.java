package com.presentation.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class BookListResponseModel {

    @JsonProperty("books")
    private List<BookResponseModel> books;
}
