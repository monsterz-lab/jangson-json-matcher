package org.monsterzlab.jangson.jsonmatcher;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class HamcrestMatcherTest {

    private static final int WIREMOCK_PORT = 5959;

    @ClassRule
    public static final WireMockClassRule WIREMOCK_RULE = new WireMockClassRule(WIREMOCK_PORT);

    @Before
    public void setUp() throws Exception {

        stubFor(get(urlEqualTo("/object"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"first\": \"hello\", \"second\": \"world!\"}")));

        stubFor(get(urlEqualTo("/array"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[1, 2, 3]")));
    }

    @Test
    public void testJsonObjectMatcher() throws Exception {
        String expected = "{\"first\": \"hello\", \"second\": \"world!\"}";
        given().
            port(WIREMOCK_PORT).
        when().
            get("/object").
        then().
            body(HamcrestMatcher.match(expected));
    }

    @Test
    public void testJsonArrayMatcher() throws Exception {
        String expected = "[1,2,3]";
        given().
            port(WIREMOCK_PORT).
        when().
            get("/array").
        then().
            body(HamcrestMatcher.match(expected));
    }
}
