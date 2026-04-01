package com.example.illicitcapitalflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/monitoring")
    public String monitoring() {
        return "Monitoring Dashboard";
    }

    @GetMapping("/statistics")
    public String statistics() {
        return "Statistics Overview";
    }
}
