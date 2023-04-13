package ru.skypro.homework.service.utils;

public class UrlUtils {

    public static String userImageUrlFromId(long id) {
        return "/users/%d/image".formatted(id);
    }

}
