package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.model.Comment;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user", "ads"})
    Optional<Comment> findAllByAdsId(Long adsId);

    @Query("delete from Comment c where c.id = ?1")
    @Transactional
    @Modifying
    @Override
    void deleteById(Long id);

    @EntityGraph(attributePaths = {"user", "ads"})
    @Override
    Optional<Comment> findById(Long id);
}
