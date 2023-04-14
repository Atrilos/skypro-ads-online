package ru.skypro.homework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Slf4j
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ads")
public class Ads {
    /**
     * Первичный ключ объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Название объявления
     */
    @Column(name = "title")
    private String title;

    /**
     * Описание объявления
     */
    @Column(name = "description")
    private String description;

    /**
     * Пользователь оставивший объявление
     */
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Картинка объявления
     */
    @OneToOne(mappedBy = "ads")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private AdsImage image;

    /**
     * Цена товара
     */
    @Column(name = "price")
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ads ads = (Ads) o;
        return getId() != null && Objects.equals(getId(), ads.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
