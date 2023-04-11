package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Text;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;

import ru.skypro.homework.mapper.Mapper;
import org.springframework.stereotype.Service;

import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.security.SecurityUser;

import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final Mapper mapper;

    public ResponseWrapperComment getComments (Long adsId) {
        log.info("Get all ads created by user with id={}");

        List<CommentDTO> commentByAdsId = commentRepository.findAllByAds_Id(adsId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return  new ResponseWrapperComment(commentByAdsId.hashCode(),commentByAdsId);
    }

  public CommentDTO addComments ( Long adsId) {
      /*  List<CommentDTO> commentSave = adsRepository.findById(adsId)
      .stream().map(commentRepository.save(comment)).collect(Collectors.toList());*/
   /*  CommentDTO commentSave = commentRepository.save
              .map(mapper::toDto).orElse(null);*/

      Comment entity = mapper.toEntity(new CommentDTO());
      entity.setText(entity.getText());
      commentRepository.save(entity);
      entity.setUser(entity.getUser());
      commentRepository.save(entity);
      entity.setAds(entity.getAds());
      commentRepository.save(entity);
      entity.setId(entity.getId());
      commentRepository.saveAndFlush(entity);
      return mapper.toDto(entity);
    }

    public CommentDTO removeCommentById(Long id, Long adId) {
        log.info("Removing ad with id={}", id);
     return commentRepository.deleteByIdAndAds_Id(id,adId).map(mapper::toDto).orElse(null);
    }

    public CommentDTO updateComment(Long id, Long adId) {
        log.info("Update ad with id={} with {}", id, adId);
      /* Comment foundComment = commentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException());*/
     // mapper.createCommentToCommentPatch(patch, foundComment);
        Comment entity = mapper.toEntity(new CommentDTO());
        entity.setText(entity.getText());
        commentRepository.save(entity);
        entity.setUser(entity.getUser());
        commentRepository.save(entity);
        entity.setAds(entity.getAds());
        commentRepository.save(entity);
        entity.setId(entity.getId());
        commentRepository.saveAndFlush(entity);
        return mapper.toDto(entity);

    }


    public CommentDTO getCommentsById( Long id, Long adId) {
        log.info("getCommentsById ad with id={}", id,adId);
        return commentRepository.findByIdAndAds_Id(id, adId)
                .map(mapper::toDto).orElse(null);
  }

}
