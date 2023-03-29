package ru.skypro.homework.exception.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementNotFound extends RuntimeException {

    public ElementNotFound() {

    }

    public ElementNotFound(String textException) {

    }
}
