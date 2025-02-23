package com.baeldung.rest.karate;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import cucumber.api.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;


import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

//We need to upgrade to latest version of Karate for JDK17 --- JAVA-22250
//@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate")
public class KarateIntegrationTest {

    private static final int PORT_NUMBER = 8097;

    private static final WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().port(PORT_NUMBER));

    @BeforeClass
    public static void setUp() throws Exception {
        wireMockServer.start();

        configureFor("localhost", PORT_NUMBER);
        stubFor(get(urlEqualTo("/user/get"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));
        stubFor(post(urlEqualTo("/user/create"))
                .withHeader("content-type", equalTo("application/json"))
                .withRequestBody(containing("id"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));

    }

    @AfterClass
    public static void tearDown() throws Exception {
        wireMockServer.stop();
    }

}
