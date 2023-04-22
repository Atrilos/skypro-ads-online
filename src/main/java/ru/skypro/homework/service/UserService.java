package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.exception.WrongPasswordException;
import ru.skypro.homework.mapper.Mapper;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.SecurityUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

import static ru.skypro.homework.exception.message.AuthMessages.USER_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final Mapper mapper;
    private final PasswordEncoder encoder;
    @PersistenceContext
    private EntityManager entityManager;

    public UserDTO getUser(SecurityUser currentUser) {
        User user = currentUser.getUser();
        return mapper.toDto(user);
    }

    @Transactional
    public void updateUserImage(MultipartFile image, SecurityUser currentUser) throws IOException {

        User user = currentUser.getUser();
        try (Session session = entityManager.unwrap(Session.class)) {
            session.update(user);
        }
        Avatar avatar = userRepository
                .findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MSG))
                .getImage();
        if (avatar == null) {
            avatar = Avatar.builder()
                    .user(user)
                    .data(image.getBytes())
                    .mediaType(image.getContentType())
                    .build();
        } else {
            avatar.setData(image.getBytes());
            avatar.setMediaType(image.getContentType());
        }
        avatarRepository.save(avatar);
        user.setImage(avatar);
        saveInDbAndCache(user);
    }

    @CachePut(value = "user", key = "#result.id")
    public User saveInDbAndCache(User user) {
        return userRepository.save(user);
    }

    public void changePassword(SecurityUser currentUser, NewPasswordDTO newPasswordDTO) {
        String currentEncodedPassword = currentUser.getUser().getPassword();
        if (!encoder.matches(newPasswordDTO.getCurrentPassword(), currentEncodedPassword)) {
            throw new WrongPasswordException(newPasswordDTO.getCurrentPassword());
        }

        User user = currentUser.getUser();
        try (Session session = entityManager.unwrap(Session.class)) {
            session.update(user);
        }
        user.setPassword(encoder.encode(newPasswordDTO.getNewPassword()));

        saveInDbAndCache(user);
    }

    public UserDTO updateUser(UserDTO patch, SecurityUser currentUser) {
        User user = currentUser.getUser();
        try (Session session = entityManager.unwrap(Session.class)) {
            session.update(user);
        }
        mapper.userDtoToUserPatch(patch, user);

        return mapper.toDto(saveInDbAndCache(user));
    }
}
