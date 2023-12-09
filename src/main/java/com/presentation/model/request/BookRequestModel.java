package com.presentation.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class BookRequestModel {

    private String barcode;
    private String author;
    private String category;
    private String bookName;

}
