package io.github.chubbyhippo.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.channels.ClosedChannelException;

@Service
public class RemoteHelloService {

    private static final Logger log = LoggerFactory.getLogger(RemoteHelloService.class);

    private final RestClient restClient;

    public RemoteHelloService(RestClient.Builder restClientBuilder,
                              // must be here to make sure the value is injected during application startup
                              @Value("${hello.service.url}") String url) {
        this.restClient = restClientBuilder
                .baseUrl(url)
                .build();
    }

    @Retryable
    HelloResponse getHelloWithRetry(HelloRequest request) {
        log.info("Get hello from remote server");
        return restClient.get()
                .uri("/hello", request.name())
                .retrieve()
                .body(HelloResponse.class);
    }
}
