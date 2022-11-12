package pl.jhonylemon.memewebsite.security.component;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String tokenPrefix;
    private Integer accessTokenExpirationAfterDays;
    private Integer refreshTokenExpirationAfterDays;
    private String claim;
}
