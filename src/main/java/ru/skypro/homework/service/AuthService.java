package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.LoginReqDTO;
import ru.skypro.homework.dto.RegisterReqDTO;
import ru.skypro.homework.dto.enums.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.security.JpaUserDetailsService;

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

    public boolean register(RegisterReqDTO registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );
        return true;
    }
}
