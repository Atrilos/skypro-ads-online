package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.security.SecurityUser;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Метод для смены пароля текущего пользователя
     *
     * @param newPassword дто-объект, содержащий старый и новый пароли
     * @param currentUser текущий пользователь в виде оберточного класса {@link SecurityUser}
     * @return код 200 - при удачном изменении пароля, код 403 - при введении неверного текущего пароля
     */
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDTO newPassword,
                                         @AuthenticationPrincipal SecurityUser currentUser) {
        userService.changePassword(currentUser, newPassword);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод возвращает данные о пользователе
     *
     * @param currentUser текущий пользователь в виде оберточного класса {@link SecurityUser}
     * @return данные о пользователе в виде дто-объекта {@link UserDTO}
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal SecurityUser currentUser) {
        return ResponseEntity.ok(userService.getUser(currentUser));
    }

    /**
     * Метод обновляет данные пользователя
     *
     * @param newUser     дто-объект, содержащий данные для обновления пользователя
     * @param currentUser текущий пользователь в виде оберточного класса {@link SecurityUser}
     * @return данные о пользователе в виде дто-объекта {@link UserDTO}
     */
    @PatchMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO newUser,
                                              @AuthenticationPrincipal SecurityUser currentUser) {
        return ResponseEntity.ok(userService.updateUser(newUser, currentUser));
    }

    /**
     * Метод обновляет аватар пользователя
     *
     * @param image       аватар пользователя в виде {@link MultipartFile}
     * @param currentUser текущий пользователь в виде оберточного класса {@link SecurityUser}
     * @return код 200 - при удачном добавлении пользователя
     * @throws IOException при невозможности считать бинарные данные из {@link MultipartFile}
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam MultipartFile image,
                                             @AuthenticationPrincipal SecurityUser currentUser) throws IOException {
        userService.updateUserImage(image, currentUser);
        return ResponseEntity.ok().build();
    }
}
