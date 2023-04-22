package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.model.Comment;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.ads join fetch c.user where c.ads.id = ?1")
    Optional<Comment> findAllByAdsId(Long adsId);

    @Query("delete from Comment c where c.id = ?1")
    @Transactional
    @Modifying
    @Override
    void deleteById(Long id);

    @Query("select c from Comment c join fetch c.user join fetch c.ads where c.id = ?1")
    @Override
    Optional<Comment> findById(Long id);
}
