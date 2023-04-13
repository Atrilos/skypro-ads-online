package ru.skypro.homework.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.FullAdsDTO;
import ru.skypro.homework.dto.RegisterReqDTO;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        addAllMappings(mapper);
        mapper.getConfiguration().setSkipNullEnabled(true);

        return mapper;
    }

    private void addAllMappings(ModelMapper mapper) {
        addMappingsAdsToAdsDto(mapper);
        addMappingsAdsToFullAdsDto(mapper);
        addMappingsRegisterReqToUser(mapper);
        addMappingsCommentToCommentDto(mapper);
    }

    private void addMappingsCommentToCommentDto(ModelMapper mapper) {
        TypeMap<Comment, CommentDTO> typeMap = mapper.createTypeMap(Comment.class, CommentDTO.class);
        Converter<Long, String> idToUrl = userIdToAvatarConverter();

        typeMap.addMappings(m -> {
            m.using(idToUrl).map(src -> src.getUser().getId(), CommentDTO::setAuthorImage);
            m.map(src -> src.getUser().getId(), CommentDTO::setAuthor);
            m.map(src -> src.getUser().getFirstName(), CommentDTO::setAuthorFirstName);
            m.map(Comment::getId, CommentDTO::setPk);
        });
    }

    private void addMappingsRegisterReqToUser(ModelMapper mapper) {
        TypeMap<RegisterReqDTO, User> typeMap = mapper.createTypeMap(RegisterReqDTO.class, User.class);
        typeMap.addMappings(m -> m.map(RegisterReqDTO::getUsername, User::setEmail));
    }

    private void addMappingsAdsToAdsDto(ModelMapper mapper) {
        TypeMap<Ads, AdsDTO> typeMap = mapper.createTypeMap(Ads.class, AdsDTO.class);
        Converter<Long, String> idToUrl = adsIdToImageUrlConverter();
        typeMap.addMappings(m -> {
            m.using(idToUrl).map(Ads::getId, AdsDTO::setImage);
            m.map(src -> src.getUser().getId(), AdsDTO::setAuthor);
            m.map(Ads::getId, AdsDTO::setPk);
        });
    }

    private void addMappingsAdsToFullAdsDto(ModelMapper mapper) {
        TypeMap<Ads, FullAdsDTO> typeMap = mapper.createTypeMap(Ads.class, FullAdsDTO.class);
        Converter<Long, String> idToUrl = adsIdToImageUrlConverter();

        typeMap.addMappings(m -> {
            m.using(idToUrl).map(Ads::getId, FullAdsDTO::setImage);
            m.map(Ads::getId, FullAdsDTO::setPk);
            m.map(src -> src.getUser().getFirstName(), FullAdsDTO::setAuthorFirstName);
            m.map(src -> src.getUser().getLastName(), FullAdsDTO::setAuthorLastName);
            m.map(src -> src.getUser().getEmail(), FullAdsDTO::setEmail);
            m.map(src -> src.getUser().getPhone(), FullAdsDTO::setPhone);
        });
    }

    private Converter<Long, String> userIdToAvatarConverter() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Long source) {
                return "/users/%d/image".formatted(source);
            }
        };
    }

    private Converter<Long, String> adsIdToImageUrlConverter() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Long source) {
                return "/ads/%d/image".formatted(source);
            }
        };
    }
}
