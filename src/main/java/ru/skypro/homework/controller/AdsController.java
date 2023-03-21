package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAll() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addAds(@RequestPart(name = "properties") CreateAds createAds,
                                    @RequestPart(name = "image") MultipartFile image) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable(name = "ad_pk") Integer adId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{ad_pk}/comments")
    public ResponseEntity<ResponseWrapperComment> addComments(@PathVariable(name = "ad_pk") Integer adId,
                                                              @RequestBody Comment comment) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAds> getFullAd(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ads> updateAds(@PathVariable(name = "id") Integer id,
                                             @RequestBody CreateAds createAds) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Comment> getComments(@PathVariable("ad_pk") Integer adId,
                                         @PathVariable("id") Integer id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable("ad_pk") Integer adId,
                                         @PathVariable("id") Integer id) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Comment> updateComments(@PathVariable("ad_pk") Integer adId,
                                            @PathVariable("id") Integer id,
                                            @RequestBody Comment comment) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(/*@AuthenticationPrincipal User user*/) {
        return ResponseEntity.ok().build();
    }
}
