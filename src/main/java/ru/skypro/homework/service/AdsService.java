package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.mapper.Mapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.security.SecurityUser;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdsService {

    private final AdsRepository adsRepository;
    private final Mapper mapper;

    public ResponseWrapperAds getAdsMe(SecurityUser currentUser) {
        Long userId = currentUser.getUser().getId();
        List<AdsDTO> adsByUserId = adsRepository.findByUserId(userId).stream().map(mapper::toDto).collect(Collectors.toList());

        return new ResponseWrapperAds(adsByUserId.size(), adsByUserId);
    }
}
