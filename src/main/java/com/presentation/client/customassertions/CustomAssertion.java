package com.presentation.client.customassertions;

import io.restassured.response.Response;

import static org.testng.Assert.assertTrue;

public class CustomAssertion {

    public static void assertResponseTimeLessThan(Response response, long maxTime) {
        assertTrue(response.getTime() < maxTime,
                "Response time should be lower than " + maxTime + "ms actual time " + response.getTime() + "ms");
    }

}
