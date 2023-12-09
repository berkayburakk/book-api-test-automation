package com.presentation.specs;

import com.presentation.config.Configuration;
import com.presentation.config.ConfigurationManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class RequestSpecificationHelper {

    private final static Configuration configuration = ConfigurationManager.getConfiguration();
    public static final String APPLICATION_JSON = "application/json";

    public static RequestSpecification getDefaultJsonRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(APPLICATION_JSON)
                .setAccept(APPLICATION_JSON)
                .build();
    }

    public static RequestSpecification getJsonRequestSpecificationWithPathParam(String pathParamName, Object pathParamValue) {
        return new RequestSpecBuilder()
                .setContentType(APPLICATION_JSON)
                .setAccept(APPLICATION_JSON)
                .addPathParam(pathParamName, pathParamValue)
                .build();
    }

}
