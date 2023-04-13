package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.Mapper;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.SecurityUser;
import ru.skypro.homework.service.utils.UrlUtils;

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
    @PersistenceContext
    private EntityManager entityManager;

    public UserDTO getUser(SecurityUser currentUser) {
        User user = currentUser.getUser();
        UserDTO dto = mapper.toDto(user);
        if (user.getImage() != null) {
            dto.setImage(UrlUtils.userImageUrlFromId(user.getId()));
        }
        return dto;
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
        userRepository.save(user);
    }
}
