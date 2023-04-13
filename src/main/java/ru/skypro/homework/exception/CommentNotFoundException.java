package ru.skypro.homework.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommentNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Комментарий с id=%d не найден";

    public CommentNotFoundException(Long id) {
        super(DEFAULT_MESSAGE.formatted(id));
        log.error("{}: {}", CommentNotFoundException.class.getName(), DEFAULT_MESSAGE.formatted(id));
    }

}
