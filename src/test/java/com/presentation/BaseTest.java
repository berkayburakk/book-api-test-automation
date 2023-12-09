package com.presentation;

import com.presentation.client.requests.BookRequests;
import com.presentation.config.Configuration;
import com.presentation.config.ConfigurationManager;
import com.presentation.data.BookDataFactory;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.config;

public class BaseTest {

    protected static Configuration configuration;
    protected BookDataFactory bookDataFactory;
    protected BookRequests bookRequests;

    private static void determineLog() {
        if (configuration.logAll()) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        } else if (configuration.logAllure()) {
            RestAssured.filters(new AllureRestAssured());
        } else {
            RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        }
    }

    @BeforeSuite
    public void setupSuite() {
        configuration = ConfigurationManager.getConfiguration();
        baseURI = configuration.bookApiUri();

        determineLog();

        System.out.println(baseURI);
    }

    @BeforeClass
    public void setupClass() {
        bookDataFactory = new BookDataFactory();
        bookRequests = new BookRequests();
    }

}
