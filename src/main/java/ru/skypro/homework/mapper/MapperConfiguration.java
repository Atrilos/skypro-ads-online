package ru.skypro.homework.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.Ads;
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
        TypeMap<Ads, FullAds> typeMap = mapper.createTypeMap(Ads.class, FullAds.class);
        Converter<Long, String> idToUrl = adsIdToImageUrlConverter();

        typeMap.addMappings(m -> {
            m.using(idToUrl).map(Ads::getId, FullAds::setImage);
            m.map(Ads::getId, FullAds::setId);
            m.map(src -> src.getUser().getFirstName(), FullAds::setAuthorFirstName);
            m.map(src -> src.getUser().getLastName(), FullAds::setAuthorLastName);
            m.map(src -> src.getUser().getEmail(), FullAds::setEmail);
            m.map(src -> src.getUser().getPhone(), FullAds::setPhone);
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
