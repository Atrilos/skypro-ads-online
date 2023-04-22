package ru.skypro.homework.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WrongPasswordException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Пароль %s не совпадает с паролем в БД";

    public WrongPasswordException(String password) {
        super(DEFAULT_MESSAGE.formatted(password));
        log.error("{}: {}", WrongPasswordException.class.getName(), DEFAULT_MESSAGE.formatted(password));
    }

}
