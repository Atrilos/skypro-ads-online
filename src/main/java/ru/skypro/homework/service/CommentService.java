package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
         adsId = new Ads().getId();
        List<CommentDTO> commentByAdsId = commentRepository.findById(adsId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return  new ResponseWrapperComment(commentByAdsId.size(), commentByAdsId);
    }

  public ResponseWrapperComment addComments (CommentDTO comment) {
        Long adsId = new Ads().getId();
      /*  List<CommentDTO> commentSave = adsRepository.findById(adsId)
      .stream().map(commentRepository.save(comment)).collect(Collectors.toList());*/
       List<CommentDTO> commentSave = commentRepository.findById(adsId)
               .stream().map(mapper::toDto).collect(Collectors.toList());
    return new ResponseWrapperComment(commentSave.size(), commentSave);
    }

    public void removeCommentById(Long id) {
        log.info("Removing ad with id={}", id);
        commentRepository.deleteById(id);
    }

    public void updateComment(Long id, CommentDTO patch) {
        log.info("Update ad with id={} with {}", id, patch);
        Comment foundComment = commentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException());
        //mapper.(patch, foundComment);
        commentRepository.save(foundComment);
    }

    public ResponseWrapperComment getCommentsById(Ads ads, Long id, Long adId) {
         ads = adsRepository
                .findById(adId)
                .orElseThrow(() -> new RuntimeException());

        List<CommentDTO> getCommentById = commentRepository.findById(id)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return new ResponseWrapperComment(getCommentById.size(), getCommentById);
  }

}
