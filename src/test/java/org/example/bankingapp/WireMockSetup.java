package org.example.bankingapp;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockSetup {
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(8081);
        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/hellowiremock"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\": \"Hello, WireMock!\"}")));

        Runtime.getRuntime().addShutdownHook(new Thread(wireMockServer::stop));
    }
}