package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest {

    private ModelMapper out = new MapperConfiguration().modelMapper();

    @Test
    public void userToUserDTO() {
        User inputUser = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .firstName("A")
                .lastName("B")
                .phone("123445")
                .build();
        UserDTO expected = UserDTO.builder()
                .id(1L)
                .email("user@gmail.com")
                .firstName("A")
                .lastName("B")
                .phone("123445")
                .build();

        UserDTO actual = out.map(inputUser, UserDTO.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFieldsMatchingRegexes("image")
                .isEqualTo(expected);
        assertThat(actual.getImage()).contains("user");
    }

    @Test
    public void adsToAdsDTO() {
        User inputUser = User.builder()
                .id(123L)
                .build();
        Ads inputAds = Ads.builder()
                .id(1L)
                .description("bla")
                .title("aaa")
                .price(12)
                .user(inputUser)
                .build();
        AdsDTO expected = AdsDTO.builder()
                .id(1L)
                .title("aaa")
                .price(12)
                .author(123L)
                .build();

        AdsDTO actual = out.map(inputAds, AdsDTO.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("image")
                .isEqualTo(expected);
        assertThat(actual.getImage()).contains("ads");
    }

    @Test
    public void adsAndUserToFullAds() {
        User inputUser = User.builder()
                .id(123L)
                .firstName("A")
                .lastName("BB")
                .email("gg@gmail.com")
                .phone("123123")
                .build();
        Ads inputAds = Ads.builder()
                .id(1L)
                .description("blabla")
                .title("Yttt")
                .price(166)
                .user(inputUser)
                .build();
        FullAds expected = FullAds.builder()
                .id(inputAds.getId())
                .authorFirstName(inputUser.getFirstName())
                .authorLastName(inputUser.getLastName())
                .description(inputAds.getDescription())
                .email(inputUser.getEmail())
                .phone(inputUser.getPhone())
                .price(inputAds.getPrice())
                .title(inputAds.getTitle())
                .build();

        FullAds actual = out.map(inputAds, FullAds.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("image")
                .isEqualTo(expected);
    }
}