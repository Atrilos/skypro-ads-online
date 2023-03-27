package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.ResponseWrapperComment;

import javax.validation.constraints.Min;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class CommentController {

    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable(name = "ad_pk") @Min(1L) Integer adId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperComment> addComments(@PathVariable(name = "ad_pk") @Min(1L) Integer adId,
                                                              @RequestBody CommentDTO comment) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDTO> getComments(@PathVariable("ad_pk") @Min(1L) Integer adId,
                                                  @PathVariable("id") @Min(1L) Integer id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable("ad_pk") @Min(1L) Integer adId,
                                            @PathVariable("id") @Min(1L) Integer id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable("ad_pk") @Min(1L) Integer adId,
                                                     @PathVariable("id") @Min(1L) Integer id,
                                                     @RequestBody CommentDTO comment) {
        return ResponseEntity.ok().build();
    }

}
