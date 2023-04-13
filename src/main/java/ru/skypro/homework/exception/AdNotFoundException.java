package ru.skypro.homework.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Объявление с id=%d не найдено";

    public AdNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
        log.error("{}: {}", AdNotFoundException.class.getName(), DEFAULT_MESSAGE.formatted(id));
    }

}
