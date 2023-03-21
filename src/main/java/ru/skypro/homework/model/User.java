package ru.skypro.homework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;
import ru.skypro.homework.dto.enums.Role;

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
public class User {

    /**
     * Id пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * Имя пользователя
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия пользователя
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * mail пользователя
     */
    @Column(name = "email", nullable = false, unique = true)
    @NaturalId
    private String email;

    /**
     * Пароль пользователя
     */
    @Column(name = "password")
    private String password;

    /**
     * Телефон пользователя
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Дата регистрации пользователя
     */
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    /**
     * Город пользователя
     */
    @Column(name = "city")
    private String city;

    /**
     * Фото пользователя
     */
    @Column(name = "image")
    private Byte[] image;

    /**
     * Права доступа пользователя
     */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @PostPersist
    public void logUserAdded() {
        log.info(
                "Added user: email={}, role={}",
                email,
                role
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getEmail() != null && Objects.equals(getEmail(), user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}