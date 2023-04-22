package ru.skypro.homework.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
        log.error("{}: {}", UserAlreadyExistsException.class.getName(), message);
    }
}
