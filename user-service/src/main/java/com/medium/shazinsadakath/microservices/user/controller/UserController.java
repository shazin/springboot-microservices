package com.medium.shazinsadakath.microservices.user.controller;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ServletWebServerApplicationContext webServerApplicationContext;

    public UserController(ServletWebServerApplicationContext webServerApplicationContext) {
        this.webServerApplicationContext = webServerApplicationContext;
    }

    @GetMapping
    public Map<String, String> get() {
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("name", "Shazin");
        try {
            userDetails.put("address", InetAddress.getLocalHost().getHostName() + ":" + webServerApplicationContext.getWebServer().getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return userDetails;
    }
}
