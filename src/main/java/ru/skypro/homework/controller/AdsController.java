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
    public ResponseEntity<AdsDTO> addAds(@RequestPart(name = "properties") CreateAdsDTO createAds,
                                         @RequestPart(name = "image") MultipartFile image) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDTO> getFullAd(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdsDTO> updateAds(@PathVariable(name = "id") Integer id,
                                            @RequestBody CreateAdsDTO createAds) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(/*@AuthenticationPrincipal User user*/) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseWrapperAds> updateAdsImage(@PathVariable("id") Integer id,
                                                             @RequestParam MultipartFile image) {
        return ResponseEntity.ok().build();
    }
}
