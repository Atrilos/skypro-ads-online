package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.service.CommentService;

import javax.validation.constraints.Min;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable(name = "ad_pk") @Min(1L) Long adId) {
        return ResponseEntity.ok(commentService.getComments(adId));
    }

    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<CommentDTO> addComments(@PathVariable(name = "ad_pk") @Min(1L) Long adId,
                                                              @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(commentService.addComments(adId));
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("ad_pk") @Min(1L) Long adId,
                                                  @PathVariable("id") @Min(1L)   Long id) {

        return ResponseEntity.ok(commentService.getCommentsById(id,adId));
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable("ad_pk") @Min(1L)Long adId,
                                            @PathVariable("id") @Min(1L)   Long id) {
        commentService.removeCommentById(id, adId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable("ad_pk") @Min(1L) Long adId,
                                                     @PathVariable("id") @Min(1L)    Long id,
                                                     @RequestBody CommentDTO comment) {
        commentService.updateComment(id, adId);
        return ResponseEntity.ok().build();
    }

}
