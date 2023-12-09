package com.presentation.client.requests;

import com.presentation.client.constants.Endpoints;
import io.restassured.response.Response;

import static com.presentation.specs.RequestSpecificationHelper.getDefaultJsonRequestSpecification;
import static com.presentation.specs.RequestSpecificationHelper.getJsonRequestSpecificationWithPathParam;

public class BookRequests extends GenericRequests {

    public Response getBooks() {
        return get(
                getDefaultJsonRequestSpecification(), Endpoints.BOOKS);
    }

    public Response getBookById(Integer id) {
        return get(
                getJsonRequestSpecificationWithPathParam("id", id),Endpoints.BOOKS_ID);
    }

    public Response postBook(Object body) {
        return post(
                getDefaultJsonRequestSpecification(), Endpoints.BOOKS, body);
    }

    public Response putBook(Object body, Integer id) {
        return put(
                getJsonRequestSpecificationWithPathParam("id",id), Endpoints.BOOKS_ID, body);
    }

    public Response deleteBookById(Integer id) {
        return delete(
                getJsonRequestSpecificationWithPathParam("id", id), Endpoints.BOOKS_ID);
    }

}