package pl.jhonylemon.memewebsite.security.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.jhonylemon.memewebsite.security.component.JwtProperties;
import pl.jhonylemon.memewebsite.security.filter.JwtAuthenticationFilter;
import pl.jhonylemon.memewebsite.security.filter.JwtAuthorizationFilter;

import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final JwtProperties jwtConfiguration;
    @Autowired
    @Qualifier("secretKeyAccessToken")
    private Algorithm secretKeyAccessToken;
    @Autowired
    @Qualifier("secretKeyRefreshToken")
    private Algorithm secretKeyRefreshToken;
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfiguration())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtConfiguration, secretKeyAccessToken, secretKeyRefreshToken))
                .addFilterBefore(new JwtAuthorizationFilter(jwtConfiguration, secretKeyAccessToken), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                .anyRequest().permitAll();
                //.anyRequest().authenticated();
        return http.build();
    }

    private static CorsConfigurationSource corsConfiguration() {
        return request -> {
            CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
            corsConfig.setAllowedOrigins(Collections.singletonList("*"));
            corsConfig.setAllowedHeaders(Collections.singletonList("*"));
            corsConfig.setAllowedMethods(Collections.singletonList("*"));
            return corsConfig;
        };
    }

}