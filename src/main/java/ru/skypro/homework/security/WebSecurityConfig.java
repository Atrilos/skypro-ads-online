package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.security.components.CustomAccessDeniedHandler;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login", "/register",
            "/ads/**", "/users/*/image"
    };

    private final AuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .mvcMatchers(AUTH_WHITELIST).permitAll()
                .mvcMatchers("/ads/**", "/users/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        http.cors();
        return http.build();
    }

    /**
     * Шифровщик паролей для работы Spring Security
     *
     * @return Реализация PasswordEncoder в виде объекта BCryptPasswordEncoder
     * @see <a href="https://hackerthink.com/solutions/recommended-of-rounds-for-bcrypt/">Recommended number of round for bcrypt</a>
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }
}

