package ru.skypro.homework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Сущность для сохранения аватаров
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Slf4j
@Table(name = "avatar")
public class Avatar {
    /**
     * Id пользователя, без генерации (см. {@link javax.persistence.MapsId})
     */
    @Id
    private Long id;
    /**
     * Медиа тип для использования в accept-header
     */
    @Column(name = "media_type")
    private String mediaType;
    /**
     * Бинарные данные аватара
     */
    @Column(name = "data")
    @Lob
    private byte[] data;
    /**
     * Односторонняя OneToOne-связь с пользователем
     */
    @OneToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @MapsId
    private User user;

    @PostPersist
    public void logAvatarAdded() {
        log.info(
                "Added avatar: userId={}, mediaType={}",
                id,
                mediaType
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Avatar avatar = (Avatar) o;
        return getId() != null && Objects.equals(getId(), avatar.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}