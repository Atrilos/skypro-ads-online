package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.Mapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.security.SecurityUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final Mapper mapper;
    private final Clock clock;
    @PersistenceContext
    private EntityManager entityManager;

    public ResponseWrapperComment getComments(Long adsId) {
        log.info("Get all comments of ad with id={}", adsId);

        List<CommentDTO> commentsByAdsId = commentRepository.findAllByAdsId(adsId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return new ResponseWrapperComment(commentsByAdsId.hashCode(), commentsByAdsId);
    }

    @Transactional
    public CommentDTO addComments(Long adsId, CommentDTO commentDTO, SecurityUser currentUser) {
        log.info("Save comment for ad with id={} with content={}", adsId, commentDTO);

        User user = currentUser.getUser();
        try (Session session = entityManager.unwrap(Session.class)) {
            session.update(user);
        }
        Ads ads = adsRepository
                .findById(adsId)
                .orElseThrow(() -> new AdNotFoundException(adsId));
        Comment entity = mapper.toEntity(commentDTO);
        entity.setUser(user);
        entity.setCreatedAt(LocalDateTime.now(clock));
        entity.setAds(ads);

        commentRepository.saveAndFlush(entity);

        return mapper.toDto(entity);
    }

    public void removeCommentById(Long adId, Long commentId) {
        log.info("Removing comment with id={} from ad with id={}", commentId, adId);
        commentRepository.deleteById(commentId);
    }

    public CommentDTO updateComment(Long id, CommentDTO patch) {
        log.info("Update comment with id={} with={}", id, patch);

        Comment foundComment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        mapper.commentDtoToCommentPatch(patch, foundComment);

        return mapper.toDto(commentRepository.save(foundComment));
    }


}
