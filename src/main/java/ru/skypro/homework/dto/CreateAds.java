package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAds {
    private final String description;
    private final Integer price;
    private final String title;
}
