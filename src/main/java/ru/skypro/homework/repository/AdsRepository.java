package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ads;

import java.util.List;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT a FROM Ads a WHERE a.user.id = ?1")
    List<Ads> findByUserId(Long userId);
}
