package com.medium.shazinsadakath.microservices.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final RestTemplate restTemplate;

    public AccountController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public Map<String, String> get() {
        Map<String, String> userResponse = restTemplate.getForObject("http://user-service/users", Map.class);
        Map<String, String> accountDetails = new HashMap<>();
        accountDetails.putAll(userResponse);
        accountDetails.put("name", userResponse.get("name") + "-account");
        return accountDetails;
    }


}
