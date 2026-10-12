package com.example.illicitcapitalflow.controller;

import com.example.illicitcapitalflow.screening.PersonScreeningRequest;
import com.example.illicitcapitalflow.screening.PersonScreeningResult;
import com.example.illicitcapitalflow.screening.PersonScreeningService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/screening")
public class ScreeningController {

    private final PersonScreeningService screeningService;

    public ScreeningController(PersonScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping("/persons")
    public PersonScreeningResult screenPerson(@Valid @RequestBody PersonScreeningRequest request) {
        return screeningService.screen(request);
    }
}
