package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.dto.enums.Role;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.AdsImage;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;

class MapperTest {

    private final ModelMapper out = new MapperConfiguration().modelMapper();
    private final Clock clock = Clock
            .fixed(LocalDateTime.of(LocalDate.ofEpochDay(40L), LocalTime.MIN)
                    .atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());


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
    }

    @Test
    public void userDTOToUser() {
        UserDTO inputUser = UserDTO.builder()
                .id(1L)
                .email("user@gmail.com")
                .firstName("A")
                .lastName("B")
                .phone("123445")
                .image("231231")
                .build();
        User expected = User.builder()
                .id(1L)
                .email("user@gmail.com")
                .firstName("A")
                .lastName("B")
                .phone("123445")
                .build();

        User actual = out.map(inputUser, User.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("image")
                .isEqualTo(expected);
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
                .pk(1L)
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
    public void adsToFullAds() {
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
        FullAdsDTO expected = FullAdsDTO.builder()
                .pk(inputAds.getId())
                .authorFirstName(inputUser.getFirstName())
                .authorLastName(inputUser.getLastName())
                .description(inputAds.getDescription())
                .email(inputUser.getEmail())
                .phone(inputUser.getPhone())
                .price(inputAds.getPrice())
                .title(inputAds.getTitle())
                .build();

        FullAdsDTO actual = out.map(inputAds, FullAdsDTO.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("image")
                .isEqualTo(expected);
    }

    @Test
    public void registerReqToUser() {
        RegisterReqDTO inputRegisterInfo = RegisterReqDTO.builder()
                .username("gg@gmail.com")
                .firstName("A")
                .lastName("BB")
                .phone("123123")
                .role(Role.USER)
                .build();
        User expected = User.builder()
                .email(inputRegisterInfo.getUsername())
                .firstName(inputRegisterInfo.getFirstName())
                .lastName(inputRegisterInfo.getLastName())
                .phone(inputRegisterInfo.getPhone())
                .role(Role.USER)
                .build();

        User actual = out.map(inputRegisterInfo, User.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void createAdsToAds() {
        CreateAdsDTO inputCreateAds = CreateAdsDTO.builder()
                .description("bla")
                .price(1255)
                .title("Aaaa")
                .build();
        Ads expected = Ads.builder()
                .description("bla")
                .price(1255)
                .title("Aaaa")
                .build();

        Ads actual = out.map(inputCreateAds, Ads.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void createAdsToAdsPatchMapping() {
        User mockUser = User.builder()
                .id(1L)
                .email("a@a.com")
                .firstName("Alex")
                .lastName("Keen")
                .build();
        AdsImage mockImage = AdsImage.builder()
                .id(10L)
                .data(new byte[]{1, 2, 3, 5, 6})
                .mediaType("png")
                .build();
        Ads existedAds = Ads.builder()
                .id(10L)
                .title("Aaa")
                .description("Bbb")
                .user(mockUser)
                .image(mockImage)
                .price(100)
                .build();
        CreateAdsDTO inputCreateAds = CreateAdsDTO.builder()
                .description("bla")
                .price(1255)
                .build();
        Ads expected = Ads.builder()
                .id(10L)
                .title("Aaa")
                .description("bla")
                .user(mockUser)
                .image(mockImage)
                .price(1255)
                .build();

        out.map(inputCreateAds, existedAds);

        assertThat(existedAds)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void commentDTOToComment() {
        CommentDTO inputCommentDTO = CommentDTO.builder()
                .author(1L)
                .authorImage("http://localhost/users/1/image")
                .authorFirstName("AA")
                .createdAt(LocalDateTime.now(clock))
                .pk(123L)
                .text("Aaaa")
                .build();
        Comment expected = Comment.builder()
                .createdAt(inputCommentDTO.getCreatedAt())
                .text(inputCommentDTO.getText())
                .build();

        Comment actual = out.map(inputCommentDTO, Comment.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void commentToCommentDTO() {
        User inputUser = User.builder()
                .id(123L)
                .firstName("A")
                .lastName("BB")
                .email("gg@gmail.com")
                .phone("123123")
                .build();
        Comment inputComment = Comment.builder()
                .id(1L)
                .text("aaa")
                .createdAt(LocalDateTime.now(clock))
                .user(inputUser)
                .build();

        CommentDTO expected = CommentDTO.builder()
                .text(inputComment.getText())
                .createdAt(inputComment.getCreatedAt())
                .author(inputUser.getId())
                .authorImage("http://localhost:8080/users/%d/image".formatted(inputUser.getId()))
                .authorFirstName(inputUser.getFirstName())
                .pk(inputComment.getId())
                .build();

        CommentDTO actual = out.map(inputComment, CommentDTO.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("authorImage")
                .isEqualTo(expected);
    }
}