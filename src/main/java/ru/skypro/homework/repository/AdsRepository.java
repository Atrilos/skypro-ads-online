package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.model.Ads;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Long> {

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT a FROM Ads a WHERE a.user.id = ?1")
    List<Ads> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "image"})
    @Override
    Optional<Ads> findById(Long id);

    @Query("delete from Ads a where a.id = ?1")
    @Transactional
    @Modifying
    @Override
    void deleteById(Long id);
}
