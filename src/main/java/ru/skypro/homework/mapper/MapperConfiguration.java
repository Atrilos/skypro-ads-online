package ru.skypro.homework.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

@Configuration
public class MapperConfiguration {

    @Value("${server.port}")
    private String port;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        addAllMappings(mapper);
        return mapper;
    }

    private void addAllMappings(ModelMapper mapper) {
        addMappingsUserToUserDto(mapper);
        addMappingsAdsToAdsDto(mapper);
        addMappingsAdsToFullAdsDto(mapper);
        addMappingsRegisterReqToUser(mapper);
        addMappingsCommentToCommentDto(mapper);
    }

    private void addMappingsCommentToCommentDto(ModelMapper mapper) {
        TypeMap<Comment, CommentDTO> typeMap = mapper.createTypeMap(Comment.class, CommentDTO.class);
        Converter<Long, String> idToUrl = new AbstractConverter<>() {
            @Override
            protected String convert(Long source) {
                return "http://localhost:%s/users/%d/image".formatted(port, source);
            }
        };
        typeMap.addMappings(m -> {
            m.map(src -> src.getUser().getId(), CommentDTO::setAuthor);
            m.map(src -> src.getUser().getFirstName(), CommentDTO::setAuthorFirstName);
            m.map(Comment::getId, CommentDTO::setPk);
            m.using(idToUrl).map(src -> src.getUser().getId(), CommentDTO::setAuthorImage);
        });
    }

    private void addMappingsRegisterReqToUser(ModelMapper mapper) {
        TypeMap<RegisterReqDTO, User> typeMap = mapper.createTypeMap(RegisterReqDTO.class, User.class);
        typeMap.addMappings(m -> m.map(RegisterReqDTO::getUsername, User::setEmail));
    }

    private void addMappingsUserToUserDto(ModelMapper mapper) {
        TypeMap<User, UserDTO> typeMap = mapper.createTypeMap(User.class, UserDTO.class);
        Converter<Long, String> idToUrl = new AbstractConverter<>() {
            @Override
            protected String convert(Long source) {
                return "http://localhost:%s/users/%d/image".formatted(port, source);
            }
        };
        typeMap.addMappings(m -> m.using(idToUrl).map(User::getId, UserDTO::setImage));
    }

    private void addMappingsAdsToAdsDto(ModelMapper mapper) {
        TypeMap<Ads, AdsDTO> typeMap = mapper.createTypeMap(Ads.class, AdsDTO.class);
        Converter<Long, String> idToUrl = adsIdToImageUrlConverter();
        typeMap.addMappings(m -> {
            m.using(idToUrl).map(Ads::getId, AdsDTO::setImage);
            m.map(src -> src.getUser().getId(), AdsDTO::setAuthor);
            m.map(Ads::getId, AdsDTO::setId);
        });
    }

    private void addMappingsAdsToFullAdsDto(ModelMapper mapper) {
        TypeMap<Ads, FullAdsDTO> typeMap = mapper.createTypeMap(Ads.class, FullAdsDTO.class);
        Converter<Long, String> idToUrl = adsIdToImageUrlConverter();

        typeMap.addMappings(m -> {
            m.using(idToUrl).map(Ads::getId, FullAdsDTO::setImage);
            m.map(Ads::getId, FullAdsDTO::setId);
            m.map(src -> src.getUser().getFirstName(), FullAdsDTO::setAuthorFirstName);
            m.map(src -> src.getUser().getLastName(), FullAdsDTO::setAuthorLastName);
            m.map(src -> src.getUser().getEmail(), FullAdsDTO::setEmail);
            m.map(src -> src.getUser().getPhone(), FullAdsDTO::setPhone);
        });
    }

    private Converter<Long, String> adsIdToImageUrlConverter() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Long source) {
                return "http://localhost:%s/ads/%d/image".formatted(port, source);
            }
        };
    }
}
