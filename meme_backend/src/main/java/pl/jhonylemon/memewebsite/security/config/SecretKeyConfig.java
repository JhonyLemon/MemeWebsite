package pl.jhonylemon.memewebsite.security.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecretKeyConfig {

    @Value("${jwt.secretKeyAccessToken}")
    private String secretKeyAccessToken;
    @Value("${jwt.secretKeyRefreshToken}")
    private String secretKeyRefreshToken;
    @Bean
    public Algorithm secretKeyAccessToken() {
        return Algorithm.HMAC256(secretKeyAccessToken.getBytes());
    }

    @Bean
    public Algorithm secretKeyRefreshToken(){
        return Algorithm.HMAC256(secretKeyRefreshToken.getBytes());
    }
}
