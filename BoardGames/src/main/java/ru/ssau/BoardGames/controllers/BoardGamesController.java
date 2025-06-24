package ru.ssau.BoardGames.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class BoardGamesController {
@GetMapping("/hello")
    private String hello(){
        return "hello";
    }
}

