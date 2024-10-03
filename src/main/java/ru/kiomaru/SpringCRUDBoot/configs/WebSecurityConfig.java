package ru.kiomaru.SpringCRUDBoot.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.kiomaru.SpringCRUDBoot.enums.RoleNames;
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

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("usertest")
//                .password(passwordEncoder().encode("usertest"))
//                .authorities(RoleNames.USER.name())
//                .build();
//        UserDetails admin = User.withUsername("admintest")
//                .password(passwordEncoder().encode("admintest"))
//                .authorities(RoleNames.ADMIN.name(), RoleNames.USER.name())
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

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
