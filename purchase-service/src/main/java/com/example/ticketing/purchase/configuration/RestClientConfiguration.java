package com.example.ticketing.purchase.configuration;

import com.example.ticketing.purchase.client.StockClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RestClientConfiguration {

    @Value("${stock.service.url}")
    private String stockServiceUrl;

    @Bean
    public StockClient stockClient() {
        ClientHttpRequestFactory factory = createRequestFactory();

        RestClient restClient = RestClient.builder()
                .baseUrl(stockServiceUrl)
                .requestFactory(factory)
                .build();

        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return proxyFactory.createClient(StockClient.class);
    }

    private ClientHttpRequestFactory createRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(3))
                .withReadTimeout(Duration.ofSeconds(3));

        return ClientHttpRequestFactoryBuilder.detect().build(settings);
    }
}
