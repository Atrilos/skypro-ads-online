package ru.skypro.homework.model;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Модель пользователя
 */
@Getter
@Setter
@ToString

@Entity
public class User {

    /**
     * id пользователя
     */
    @Id
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
     * mail пользователя
     */
    String email;

    /**
     * пароль пользователя
     */
    String password;

    /**
     * телефон пользователя
     */
    String phone;

    /**
     * дата регистрации пользователя
     */
    LocalDateTime regDate;

    /**
     * город пользователя
     */
    String city;

    /**
     * фото пользователя
     */
    String image;

    /**
     * Права доступа пользователя
     */
    private Role role;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getFirstName(), that.getFirstName())
                && Objects.equals(getLastName(), that.getLastName())
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getPassword(), that.getPassword())
                && Objects.equals(getPhone(), that.getPhone())
                && Objects.equals(getRegDate(), that.getRegDate())
                && Objects.equals(getCity(), that.getCity())
                && Objects.equals(getImage(), that.getImage())
                && getRole() == that.getRole();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}