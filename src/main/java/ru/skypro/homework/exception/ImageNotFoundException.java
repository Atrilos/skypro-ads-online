package ru.skypro.homework.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Изображение с id=%d не найдено";

    public ImageNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
        log.error("{}: {}", ImageNotFoundException.class.getName(), DEFAULT_MESSAGE.formatted(id));
    }

}
