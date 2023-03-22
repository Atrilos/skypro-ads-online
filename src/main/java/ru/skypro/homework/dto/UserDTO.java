package ru.skypro.homework.dto;

import lombok.*;

import java.time.LocalDate;


/**
 * DTO пользователя
 */
@Data
public class UserDTO {

  /**
   * Id пользователя
   */
  private Integer id;

  /**
   * Имя пользователя
   */
  private String firstName;


  /**
   * Фамилия пользователя
   */
  private String lastName;

  /**
   * Email пользователя
   */
  private String email;

  /**
   * Телефон пользователя
   */
  private String phone;

  /**
   * Дата регистрации пользователя
   */
  private LocalDate regDate;

  /**
   * Город пользователя
   */
  private String city;

  /**
   * Фото пользователя
   */
  private String image;

}