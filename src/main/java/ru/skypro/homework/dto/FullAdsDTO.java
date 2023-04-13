package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullAdsDTO {
    // От пользователя
    private String authorFirstName;
    private String authorLastName;
    private String email;
    private String phone;

    // От объявления
    private Long pk;
    private String title;
    private String description;
    private Integer price;
    private String image;
}
