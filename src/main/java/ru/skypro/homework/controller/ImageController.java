package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.service.ImageService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * Метод возвращает аватар пользователя
     *
     * @param id первичный ключ пользователя
     * @return бинарные данные аватара пользователя
     */
    @GetMapping(value = "/users/{id}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable("id") Long id) {
        return imageService.getAvatarImage(id);
    }

    /**
     * Метод возвращает картинку объявления
     *
     * @param id первичный ключ объявления
     * @return бинарные данные картинки объявления
     */
    @GetMapping(value = "/ads/{id}/image")
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") Long id) {
        return imageService.getAdsImage(id);
    }

}
