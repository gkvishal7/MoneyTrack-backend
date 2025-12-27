package com.vishal.MoneyTrack.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}