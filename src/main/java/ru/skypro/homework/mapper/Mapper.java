package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.*;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final ModelMapper mapper;

    public UserDTO toDto(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }

    public User toUserEntity(RegisterReqDTO registerReqDTO) {
        return mapper.map(registerReqDTO, User.class);
    }

    public Ads toEntity(CreateAdsDTO createAdsDTO) {
        return mapper.map(createAdsDTO, Ads.class);
    }

    public AdsDTO toDto(Ads ads) {
        return mapper.map(ads, AdsDTO.class);
    }

    public Comment toEntity(CommentDTO commentDTO) {
        return mapper.map(commentDTO, Comment.class);
    }

    public CommentDTO toDto(Comment comment) {
        return mapper.map(comment, CommentDTO.class);
    }

    public FullAdsDTO toFullAdsDto(Ads ads) {
        return mapper.map(ads, FullAdsDTO.class);
    }

    public void createAdsToAdsPatch(CreateAdsDTO createAdsDTO, Ads targetAds) {
        mapper.map(createAdsDTO, targetAds);
    }

    public void commentDtoToCommentPatch(CommentDTO commentDTO, Comment targetComment) {
        mapper.map(commentDTO, targetComment);
    }

    public void userDtoToUserPatch(UserDTO userDTO, User targetUser) {
        mapper.map(userDTO, targetUser);
    }

    public AdsImage toAdsImageEntity(MultipartFile image) throws IOException {
        return AdsImage.builder()
                .mediaType(image.getContentType())
                .data(image.getBytes())
                .build();
    }

    public Avatar toAvatarEntity(MultipartFile image) throws IOException {
        return Avatar.builder()
                .mediaType(image.getContentType())
                .data(image.getBytes())
                .build();
    }
}
