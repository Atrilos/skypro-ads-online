package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfiguration {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager() {
        ProviderManager manager = new ProviderManager(authenticationProvider());
        manager.setAuthenticationEventPublisher(authenticationEventPublisher());
        return manager;
    }

    private AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * Шифровщик паролей для работы Spring Security
     * @return Реализация PasswordEncoder в виде объекта BCryptPasswordEncoder
     * @see <a href="https://hackerthink.com/solutions/recommended-of-rounds-for-bcrypt/">Recommended number of round for bcrypt</a>
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }
}
