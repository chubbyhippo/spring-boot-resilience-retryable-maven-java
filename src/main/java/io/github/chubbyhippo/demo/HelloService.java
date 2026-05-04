package io.github.chubbyhippo.demo;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private final RemoteHelloService remoteHelloService;

    public HelloService(RemoteHelloService remoteHelloService) {
        this.remoteHelloService = remoteHelloService;
    }

    public HelloResponse getHello(HelloRequest helloRequest) {
        try {
            return remoteHelloService.getHelloWithRetry(helloRequest);
        } catch (Exception e) {
            return new HelloResponse("Cannot get a message from remote server");
        }
    }
}
