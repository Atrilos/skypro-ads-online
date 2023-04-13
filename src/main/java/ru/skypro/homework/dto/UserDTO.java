package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * DTO пользователя
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /**
     * Id пользователя
     */
    private Long id;

    /**
     * Email пользователя
     */
    @Email
    @NotNull
    private String email;

    /**
     * Имя пользователя
     */
    @Size(min = 3)
    private String firstName;

    /**
     * Фамилия пользователя
     */
    @Size(min = 3)
    private String lastName;

    /**
     * Телефон пользователя
     */
    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    /**
     * Фото пользователя
     */
    private String image;

}