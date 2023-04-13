package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdsDTO {
    @Size(min = 8)
    private String description;
    @Min(value = 0L, message = "Цена не может быть отрицательной")
    private Integer price;
    @NotNull
    @Size(min = 3)
    private String title;
}
