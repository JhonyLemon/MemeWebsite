package pl.jhonylemon.memewebsite.security.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.jhonylemon.memewebsite.security.component.JwtProperties;
import pl.jhonylemon.memewebsite.security.filter.JwtAuthenticationFilter;
import pl.jhonylemon.memewebsite.security.filter.JwtAuthorizationFilter;
import pl.jhonylemon.memewebsite.service.accountpermission.admin.AdminAccountPermissionService;

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
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtConfiguration, secretKeyAccessToken, secretKeyRefreshToken))
                .addFilterBefore(new JwtAuthorizationFilter(jwtConfiguration, secretKeyAccessToken), JwtAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/guest/**").permitAll()
                .antMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.POST,"/user/**").hasAnyAuthority(accountPermissionService.userWritePermission().getPermission())
                .antMatchers(HttpMethod.PUT,"/user/**").hasAnyAuthority(accountPermissionService.userEditPermission().getPermission())
                .antMatchers(HttpMethod.DELETE,"/user/**").hasAnyAuthority(accountPermissionService.userDeletePermission().getPermission())
                .antMatchers(HttpMethod.GET,"/user/**").hasAnyAuthority(accountPermissionService.userReadPermission().getPermission())

                .antMatchers(HttpMethod.POST,"/moderator/**").hasAnyAuthority(accountPermissionService.moderatorWritePermission().getPermission())
                .antMatchers(HttpMethod.PUT,"/moderator/**").hasAnyAuthority(accountPermissionService.moderatorEditPermission().getPermission())
                .antMatchers(HttpMethod.DELETE,"/moderator/**").hasAnyAuthority(accountPermissionService.moderatorDeletePermission().getPermission())
                .antMatchers(HttpMethod.GET,"/moderator/**").hasAnyAuthority(accountPermissionService.moderatorReadPermission().getPermission())

                .antMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(accountPermissionService.adminWritePermission().getPermission())
                .antMatchers(HttpMethod.PUT,"/admin/**").hasAnyAuthority(accountPermissionService.adminEditPermission().getPermission())
                .antMatchers(HttpMethod.DELETE,"/admin/**").hasAnyAuthority(accountPermissionService.adminDeletePermission().getPermission())
                .antMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(accountPermissionService.adminReadPermission().getPermission())
                .anyRequest().authenticated();
        return http.build();
    }
}
