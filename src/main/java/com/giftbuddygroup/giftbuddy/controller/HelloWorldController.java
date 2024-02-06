package com.giftbuddygroup.giftbuddy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/helloworld")
public class HelloWorldController {

    @GetMapping
    public String helloWorld() {
        return "Hello World from API!";
    }
}
