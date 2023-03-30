package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateAdsDTO;
import ru.skypro.homework.dto.FullAdsDTO;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.mapper.Mapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.AdsImage;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsImageRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.security.SecurityUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdsService {

    private final AdsRepository adsRepository;
    private final AdsImageRepository adsImageRepository;
    private final Mapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    public ResponseWrapperAds getAdsMe(SecurityUser currentUser) {
        log.info("Get all ads created by user with id={}", currentUser.getUser().getId());
        Long userId = currentUser.getUser().getId();
        List<AdsDTO> adsByUserId = adsRepository.findByUserId(userId).stream().map(mapper::toDto).collect(Collectors.toList());

        return new ResponseWrapperAds(adsByUserId.size(), adsByUserId);
    }

    public ResponseWrapperAds getAll() {
        log.info("Getting all ads");
        List<AdsDTO> allAds = adsRepository.findAll().stream().map(mapper::toDto).toList();
        return new ResponseWrapperAds(allAds.size(), allAds);
    }

    public FullAdsDTO getFullAd(Long id) {
        log.info("Get ad with full info for Ad id={}", id);
        Ads foundAd = adsRepository
                .findById(id)
                .orElseThrow(() -> new AdNotFoundException(id));
        return mapper.toFullAdsDto(foundAd); // TODO
    }

    @Transactional
    public void removeAdById(Long id) {
        log.info("Removing ad with id={}", id);
        adsRepository.deleteById(id);
    }

    @Transactional
    public void updateAds(Long id, CreateAdsDTO patch) {
        log.info("Update ad with id={} with {}", id, patch);
        Ads foundAd = adsRepository
                .findById(id)
                .orElseThrow(() -> new AdNotFoundException(id));
        mapper.createAdsToAdsPatch(patch, foundAd);
        adsRepository.save(foundAd);
    }

    @Transactional
    public AdsDTO saveAdWithImage(CreateAdsDTO newAd, MultipartFile image, SecurityUser currentUser) throws IOException {
        log.info("Save ad with image from user id={} with content={}", currentUser.getUser().getId(), newAd);
        User user = currentUser.getUser();
        try (Session session = entityManager.unwrap(Session.class)) {
            session.update(user);
        }
        Ads entity = mapper.toEntity(newAd);
        entity.setUser(user);
        adsRepository.save(entity);
        AdsImage adsImage = mapper.toAdsImageEntity(image);
        adsImage.setAds(entity);
        adsImageRepository.save(adsImage);
        entity.setImage(adsImage);
        adsRepository.saveAndFlush(entity);
        return mapper.toDto(entity);
    }

    public ResponseEntity<byte[]> updateAds(Long id, MultipartFile image) throws IOException {
        log.info("Update ad image for id={}", id);
        Ads foundAd = adsRepository
                .findById(id)
                .orElseThrow(() -> new AdNotFoundException(id));
        AdsImage adsImage = mapper.toAdsImageEntity(image);
        foundAd.setImage(adsImage);
        adsRepository.save(foundAd);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(adsImage.getMediaType()))
                .body(adsImage.getData());
    }
}
