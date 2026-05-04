package io.github.chubbyhippo.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RemoteHelloService {

    private final RestClient restClient;

    public RemoteHelloService(RestClient.Builder restClientBuilder,
                              // must be here to make sure the value is injected during application startup
                              @Value("${hello.service.url}") String url) {
        this.restClient = restClientBuilder
                .baseUrl(url)
                .build();
    }

    @Retryable(includes =  { Exception.class })
    HelloResponse getHelloWithRetry(HelloRequest request) {
        return restClient.get().uri("/hello", request.name())
                .retrieve()
                .body(HelloResponse.class);
    }
}
