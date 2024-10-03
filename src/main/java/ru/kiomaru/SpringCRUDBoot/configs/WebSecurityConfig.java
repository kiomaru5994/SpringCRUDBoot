package ru.kiomaru.SpringCRUDBoot.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kiomaru.SpringCRUDBoot.service.AppUserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final AppUserDetailService appUserDetailService;

    public WebSecurityConfig(LoginSuccessHandler loginSuccessHandler, AppUserDetailService appUserDetailService) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.appUserDetailService = appUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/", "/index").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(appUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
