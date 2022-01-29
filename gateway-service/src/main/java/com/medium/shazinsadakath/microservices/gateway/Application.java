package com.medium.shazinsadakath.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RouteLocator apiRouters(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/users")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("users")
                                .setFallbackUri("forward:/fallback")))
                        .uri("lb://user-service/users"))
                .route(p -> p
                        .path("/accounts")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("accounts")
                                .setFallbackUri("forward:/fallback")))
                        .uri("lb://account-service/accounts"))
                .build();
    }

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory factory() {
        return new ReactiveResilience4JCircuitBreakerFactory();
    }

    @RequestMapping("/fallback")
    public String fallback() {
        return "fallback";
    }

}
