package ru.skypro.homework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

/**
 * Сущность для сохранения картинок в объявлениях
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Slf4j
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ads_image")
public class AdsImage {

    /**
     * Id объявления, без генерации (см. {@link javax.persistence.MapsId})
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
     * Односторонняя OneToOne-связь с объявлением
     */
    @OneToOne(
            optional = false,
            fetch = FetchType.LAZY
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @MapsId
    private Ads ads;

    @PostPersist
    public void logAdsImageAdded() {
        log.info(
                "Added ads image: adsId={}, mediaType={}",
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
