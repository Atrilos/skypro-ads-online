package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class LoginReqDTO {
    @Size(min = 8)
    private String password;
    @Email
    private String username;

}
