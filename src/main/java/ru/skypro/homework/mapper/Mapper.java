package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.*;

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
}
