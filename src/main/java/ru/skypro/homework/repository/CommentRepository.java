package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndAds_Id(Long id, Long adsId);

    Optional<Comment> deleteByIdAndAds_Id(Long id, Long adsId);

    Optional<Comment> findAllByAds_Id(Long adsId);

}
