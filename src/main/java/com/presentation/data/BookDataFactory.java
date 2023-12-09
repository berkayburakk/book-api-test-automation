package com.presentation.data;

import com.github.javafaker.Faker;
import com.presentation.client.requests.BookRequests;
import com.presentation.model.request.BookRequestModel;
import com.presentation.model.response.BookResponseModel;
import org.apache.http.HttpStatus;

public class BookDataFactory extends BookRequests {

    private final Faker faker;
    public static final Integer NOT_FOUND_ID= -1;

    public BookDataFactory() {
        faker = new Faker();
    }

    public BookRequestModel generateBookBody() {
        return BookRequestModel.builder()
                .bookName(faker.book().title())
                .category(faker.book().genre())
                .barcode(faker.code().ean8())
                .author(faker.book().author())
                .build();
    }

    public BookResponseModel createNewBook(){
        var body = generateBookBody();
        return postBook(body)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response()
                .as(BookResponseModel.class);
    }

}
