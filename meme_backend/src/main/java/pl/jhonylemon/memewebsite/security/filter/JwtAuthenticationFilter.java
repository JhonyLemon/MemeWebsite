package pl.jhonylemon.memewebsite.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationRequest;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationResponse;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.exception.authentication.AuthenticationFailedException;
import pl.jhonylemon.memewebsite.security.component.JwtProperties;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;
    private final Algorithm secretKeyAccessToken;
    private final Algorithm secretKeyRefreshToken;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            AuthenticationRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException exception) {
            throw new AuthenticationFailedException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Account user = (Account) authResult.getPrincipal();
        String accessToken = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(
                        Instant.now().plus(
                                jwtProperties.getAccessTokenExpirationAfterDays(),
                                ChronoUnit.DAYS
                        )
                )
                .withClaim(
                        jwtProperties.getClaim(),
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                ).sign(secretKeyAccessToken);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(
                        Instant.now().plus(
                                jwtProperties.getRefreshTokenExpirationAfterDays(),
                                ChronoUnit.DAYS
                        )
                )
                .sign(secretKeyRefreshToken);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(),
                new AuthenticationResponse(
                        accessToken,
                        refreshToken
                )
        );
    }
}
