package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageController {

    @GetMapping(value = "/users/{id}/image")
    public ResponseEntity<?> getUserImage(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/ads/{id}/image")
    public ResponseEntity<?> getAdsImage(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().build();
    }
}
