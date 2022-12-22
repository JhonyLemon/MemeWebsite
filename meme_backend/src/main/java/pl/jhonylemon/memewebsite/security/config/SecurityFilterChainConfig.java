package pl.jhonylemon.memewebsite.security.config;

import com.auth0.jwt.algorithms.Algorithm;
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
import pl.jhonylemon.memewebsite.service.accountpermission.admin.AdminAccountPermissionService;

import java.util.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebMvc
@Configuration
public class SecurityFilterChainConfig {
    private final JwtProperties jwtConfiguration;
    private final Algorithm secretKeyAccessToken;
    private final Algorithm secretKeyRefreshToken;
    private final AdminAccountPermissionService accountPermissionService;

    private final AuthenticationManager authenticationManager;

    public SecurityFilterChainConfig(
            JwtProperties jwtConfiguration,
            @Qualifier("secretKeyAccessToken") Algorithm secretKeyAccessToken,
            @Qualifier("secretKeyRefreshToken") Algorithm secretKeyRefreshToken,
            AdminAccountPermissionService accountPermissionService, AuthenticationManager authenticationManager) {
        this.jwtConfiguration = jwtConfiguration;
        this.secretKeyAccessToken = secretKeyAccessToken;
        this.secretKeyRefreshToken = secretKeyRefreshToken;
        this.accountPermissionService = accountPermissionService;
        this.authenticationManager = authenticationManager;
    }

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
                .antMatchers("/guest/**").permitAll()
                .antMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.POST,"/user/**").hasAnyAuthority("USER_ADD")
                .antMatchers(HttpMethod.PUT,"/user/**").hasAnyAuthority("USER_EDIT")
                .antMatchers(HttpMethod.DELETE,"/user/**").hasAnyAuthority("USER_DELETE")
                .antMatchers(HttpMethod.GET,"/user/**").hasAnyAuthority("USER_READ")

                .antMatchers(HttpMethod.POST,"/moderator/**").hasAnyAuthority("MODERATOR_ADD")
                .antMatchers(HttpMethod.PUT,"/moderator/**").hasAnyAuthority("MODERATOR_EDIT")
                .antMatchers(HttpMethod.DELETE,"/moderator/**").hasAnyAuthority("MODERATOR_DELETE")
                .antMatchers(HttpMethod.GET,"/moderator/**").hasAnyAuthority("MODERATOR_READ")

                .antMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority("ADMIN_ADD")
                .antMatchers(HttpMethod.PUT,"/admin/**").hasAnyAuthority("ADMIN_EDIT")
                .antMatchers(HttpMethod.DELETE,"/admin/**").hasAnyAuthority("ADMIN_DELETE")
                .antMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority("ADMIN_READ")
                .anyRequest().authenticated();
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