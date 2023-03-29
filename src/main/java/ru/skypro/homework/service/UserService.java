package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.service.ElementNotFound;
import ru.skypro.homework.mapper.Mapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.SecurityUser;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;

    /**
     * Получить данные пользователя
     */
    public UserDTO getUser(SecurityUser currentUser) {
        User user = currentUser.getUser();
        return mapper.toDto(user);
    }



    /**
     * Обновление данных пользователя
     */

    public UserDTO updateUser(UserDTO newUserDto, Authentication authentication) {

        String loginEmail = authentication.getName();
        User newUser = findUserEntityByEmail(loginEmail);
        Long id = newUser.getId();

        User pastUserEntity = findById(id);

        pastUserEntity.setEmail(newUser.getEmail());
        pastUserEntity.setFirstName(newUserDto.getFirstName());
        pastUserEntity.setLastName(newUserDto.getLastName());
        pastUserEntity.setPhone(newUserDto.getPhone());



        return mapper.toDto(pastUserEntity);
    }

    /**
     * Установить пароля пользователя
     */

    public NewPasswordDTO setPassword(NewPasswordDTO newPassword) {
        return null;
    }

    /**
     * Загрузить аватарку пользователя
     */

    public void updateUserImage(MultipartFile image, Authentication authentication) {

    }

    /**
     * найти пользователя по id
     *
     * @param id id пользователя
     * @return пользователь
     */
    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ElementNotFound::new);

    }

    /**
     * найти пользователя по логину (email)
     *
     * @param email email - как логин пользователя
     * @return пользователь
     */
    private User findUserEntityByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

}
