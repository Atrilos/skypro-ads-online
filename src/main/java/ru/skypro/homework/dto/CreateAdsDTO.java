package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdsDTO {
    private String description;
    @Min(value = 0L, message = "Цена не может быть отрицательной")
    private Integer price;
    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;
}
