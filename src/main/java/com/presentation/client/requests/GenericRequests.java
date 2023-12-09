package com.presentation.client.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;;

import static io.restassured.RestAssured.given;


public class GenericRequests {

    Response get(RequestSpecification reqSpec, String path) {
        return  given()
                    .spec(reqSpec)
                .when()
                    .get(path)
                .then()
                    .extract()
                    .response();
    }

    Response post(RequestSpecification reqSpec, String path, Object body) {
        return
                given()
                    .spec(reqSpec)
                    .body(body)
                .when()
                    .post(path)
                .then()
                    .extract()
                    .response();
    }

    Response put(RequestSpecification reqSpec, String path, Object body) {
        return
                given()
                    .spec(reqSpec)
                    .body(body)
                .when()
                    .put(path)
                .then()
                    .extract()
                    .response();
    }

    Response delete(RequestSpecification reqSpec, String path) {
        return
                given()
                    .spec(reqSpec)
                .when()
                    .delete(path)
                .then()
                    .extract()
                    .response();
    }

}
