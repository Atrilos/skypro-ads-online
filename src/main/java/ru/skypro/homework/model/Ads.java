package ru.skypro.homework.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Slf4j
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @OneToOne(mappedBy = "ads")
    private AdsImage image;
    private Integer price;
    private String title;
    private String description;
}
