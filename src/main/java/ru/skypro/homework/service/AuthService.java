package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.LoginReqDTO;
import ru.skypro.homework.dto.RegisterReqDTO;
import ru.skypro.homework.dto.enums.Role;
import ru.skypro.homework.exception.UserAlreadyExistsException;
import ru.skypro.homework.security.JpaUserDetailsService;

import static ru.skypro.homework.dto.enums.Role.USER;
import static ru.skypro.homework.exception.message.AuthMessages.USER_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JpaUserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public boolean login(LoginReqDTO req) {
        UserDetails foundUser = userDetailsService.loadUserByUsername(req.getUsername());
        String encryptedPassword = foundUser.getPassword();
        return encoder.matches(req.getPassword(), encryptedPassword);
    }

    public void register(RegisterReqDTO registerReq) {
        if (userDetailsService.userExists(registerReq.getUsername())) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS);
        }
        Role role = registerReq.getRole() == null ? USER : registerReq.getRole();
        registerReq.setRole(role);
        userDetailsService.saveUser(registerReq);
    }
}
