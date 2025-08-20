package com.example.ticketing.purchase.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class StockClientWiremock {

    public static void stubStockAvailable(String stockCode, Integer quantity) {
        stubFor(get(urlPathEqualTo("/api/stock"))
                .withQueryParam("stockCode", equalTo(stockCode))
                .withQueryParam("quantity", equalTo(quantity.toString()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));

        stubFor(put(urlPathEqualTo("/api/stock/reduce"))
                .withQueryParam("stockCode", equalTo(stockCode))
                .withQueryParam("quantity", equalTo(quantity.toString()))
                .willReturn(aResponse()
                        .withStatus(200)));
    }

    public static void stubStockUnavailable(String stockCode, Integer quantity) {
        stubFor(get(urlPathEqualTo("/api/stock"))
                .withQueryParam("stockCode", equalTo(stockCode))
                .withQueryParam("quantity", equalTo(quantity.toString()))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("false")));
    }
}
