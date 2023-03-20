package ru.skypro.homework.dto;

import lombok.*;


/**
 * DTO пользователя
 */
@Data

public class UserDTO {

  /**
   * id пользователя
   */
  Integer id;

  /**
   * Имя пользователя
   */
  String firstName;


  /**
   * Фамилия пользователя
   */
  String lastName;

  /**
   * телефон пользователя
   */
  String phone;

  /**
   * дата регистрации пользователя
   */
  String regDate;

  /**
   * город пользователя
   */
  String city;

  /**
   * фото пользователя
   */
  String image;

}