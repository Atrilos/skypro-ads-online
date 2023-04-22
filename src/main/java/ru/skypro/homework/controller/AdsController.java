package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateAdsDTO;
import ru.skypro.homework.dto.FullAdsDTO;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.security.SecurityUser;
import ru.skypro.homework.service.AdsService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@Validated
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdsService adsService;

    /**
     * Метод возвращает все объявления
     *
     * @return объявления всех пользователей в виде дто-объекта {@link ResponseWrapperAds}
     */
    @GetMapping()
    public ResponseEntity<ResponseWrapperAds> getAll() {
        return ResponseEntity.ok(adsService.getAll());
    }

    /**
     * Сохраняет объявление вместе с картинкой
     *
     * @param createAds   текстовые данные объявления
     * @param image       картинка объявления в виде {@link MultipartFile}
     * @param currentUser текущий пользователь в виде оберточного класса {@link SecurityUser}
     * @return данные о добавленном объявлении
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDTO> saveAdWithImage(@RequestPart(name = "properties") @Valid CreateAdsDTO createAds,
                                                  @RequestPart(name = "image") MultipartFile image,
                                                  @AuthenticationPrincipal SecurityUser currentUser) throws IOException {

        AdsDTO body = adsService.saveAdWithImage(createAds, image, currentUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }

    /**
     * Полные данные об объявлении
     *
     * @param id первичный ключ объявления
     * @return данные об объявлении в виде дто-объекта {@link FullAdsDTO}
     */
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDTO> getFullAd(@PathVariable(name = "id") @Min(1L) Long id) {
        return ResponseEntity.ok(adsService.getFullAd(id));
    }

    /**
     * Удаляет объявление
     *
     * @param id первичный ключ объявления
     * @return код 204 - если объявление было удалено или не существовало
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAds(@PathVariable(name = "id") @Min(1L) Long id,
                                       @AuthenticationPrincipal SecurityUser currentUser) {
        adsService.removeAdById(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обновляет объявление
     *
     * @param id        первичный ключ объявления
     * @param createAds текстовая информация обновления
     * @return обновленное объявление в виде дто-объекта {@link AdsDTO}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDTO> updateAds(@PathVariable(name = "id") @Min(1L) Long id,
                                            @RequestBody @Valid CreateAdsDTO createAds,
                                            @AuthenticationPrincipal SecurityUser currentUser) {
        adsService.updateAds(id, createAds, currentUser);
        return ResponseEntity.ok().build();
    }

    /**
     * Получает данные об объявлениях пользователя
     *
     * @param currentUser данные о текущем пользователе в виде оберточного объекта {@link SecurityUser}
     * @return данные об объявлениях пользователя в виде дто-объекта {@link ResponseWrapperAds}
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperAds> getAdsMe(@AuthenticationPrincipal SecurityUser currentUser) {
        return ResponseEntity.ok(adsService.getAdsMe(currentUser));
    }

    /**
     * Обновляет картинку объявления
     *
     * @param id    первичный ключ объявления
     * @param image новая картинка
     * @return добавленная картинка в виде {@link ResponseEntity}
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateAdsImage(@PathVariable("id") @Min(1L) Long id,
                                                 @RequestParam MultipartFile image,
                                                 @AuthenticationPrincipal SecurityUser currentUser) throws IOException {

        return adsService.updateAds(id, image, currentUser);
    }
}
