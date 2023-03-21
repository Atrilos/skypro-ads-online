package ru.skypro.homework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Модель пользователя
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Slf4j
@Table(name = "users")
public class UserModel {

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
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        UserModel user = (UserModel) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}