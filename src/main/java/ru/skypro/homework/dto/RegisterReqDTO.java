package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.enums.Role;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterReqDTO {
    @Email
    private String username;
    @Size(min = 6, message = "Минимальная длина пароля 6 символов")
    private String password;
    @NotBlank
    @Pattern(regexp = "[a-zA-ZА-Яа-я]*")
    private String firstName;
    @NotBlank
    @Pattern(regexp = "[a-zA-ZА-Яа-я]*")
    private String lastName;
    @Pattern(regexp = "\\+7\\s?[(]?\\d{3}[)]?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
    private Role role;
}
