package org.monsterzlab.jangson.jsonmatcher;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static io.restassured.RestAssured.given;

public class AssertJMatcherTest {

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
        Response response = given().
                port(WIREMOCK_PORT).
                when().
                get("/object");

        AssertJMatcher.assertThat(response).match(expected).statusCode(200);
    }

    @Test
    public void testJsonArrayMatcher() throws Exception {
        String expected = "[1,2,3]";
        Response response = given().
                port(WIREMOCK_PORT).
                when().
                get("/array");

        AssertJMatcher.assertThat(response).match(expected).statusCode(200);
    }
}
