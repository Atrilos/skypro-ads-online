package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.security.components.CustomAccessDeniedHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login", "/register"
    };

    private final AuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .mvcMatchers(AUTH_WHITELIST).permitAll()
                .mvcMatchers("/ads/**", "/users/**").authenticated()
                .and()
                .csrf().disable().cors().disable()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        return http.build();
    }


}

