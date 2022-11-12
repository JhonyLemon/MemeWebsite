package pl.jhonylemon.memewebsite.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class UserDetailsServiceConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

}
