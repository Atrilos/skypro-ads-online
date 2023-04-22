package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.model.AdsImage;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.repository.AdsImageRepository;
import ru.skypro.homework.repository.AvatarRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final AdsImageRepository adsImageRepository;
    private final AvatarRepository avatarRepository;


    public ResponseEntity<byte[]> getAdsImage(Long id) {
        log.info("Get adsImage from database with id={}", id);
        AdsImage adsImage = getAdsImageFromDb(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(adsImage.getMediaType()))
                .body(adsImage.getData());
    }

    public ResponseEntity<byte[]> getAvatarImage(Long id) {
        log.info("Get avatar from database with id={}", id);
        Avatar avatar = getAvatarFromDb(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    public AdsImage getAdsImageFromDb(Long id) {
        return adsImageRepository
                .findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

    public Avatar getAvatarFromDb(Long id) {
        return avatarRepository
                .findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id));
    }

}
