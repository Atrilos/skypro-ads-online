package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.security.SecurityUser;
import ru.skypro.homework.service.CommentService;

import javax.validation.constraints.Min;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable(name = "id") @Min(1L) Long adId) {
        return ResponseEntity.ok(commentService.getComments(adId));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComments(@PathVariable(name = "id") @Min(1L) Long adId,
                                                  @RequestBody CommentDTO comment,
                                                  @AuthenticationPrincipal SecurityUser currentUser) {
        return ResponseEntity.ok(commentService.addComments(adId, comment, currentUser));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComments(@PathVariable("adId") @Min(1L) Long adId,
                                            @PathVariable("commentId") @Min(1L) Long id) {
        commentService.removeCommentById(adId, id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable("adId") @Min(1L) Long adId,
                                                     @PathVariable("commentId") @Min(1L) Long id,
                                                     @RequestBody CommentDTO comment) {
        return ResponseEntity.ok(commentService.updateComment(id, comment));
    }

}
