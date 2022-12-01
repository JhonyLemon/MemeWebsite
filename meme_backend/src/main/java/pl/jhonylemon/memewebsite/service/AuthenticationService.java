package pl.jhonylemon.memewebsite.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationRefreshRequest;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationResponse;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.authentication.AuthenticationFailedException;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.security.component.JwtProperties;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
public class AuthenticationService {

    private final Algorithm secretKeyAccessToken;
    private final Algorithm secretKeyRefreshToken;
    private final AccountRepository accountRepository;
    private final JwtProperties jwtProperties;

    public AuthenticationService(
            @Qualifier("secretKeyAccessToken")Algorithm secretKeyAccessToken,
            @Qualifier("secretKeyRefreshToken")Algorithm secretKeyRefreshToken,
            AccountRepository accountRepository,
            JwtProperties jwtProperties) {
        this.secretKeyAccessToken = secretKeyAccessToken;
        this.secretKeyRefreshToken = secretKeyRefreshToken;
        this.accountRepository = accountRepository;
        this.jwtProperties = jwtProperties;
    }

    public AuthenticationResponse refreshToken(AuthenticationRefreshRequest request) {
        if(request==null || request.getRefreshToken()==null){
            throw new AuthenticationFailedException();
        }
        try{
            JWTVerifier verifier = JWT.require(secretKeyRefreshToken).build();
            DecodedJWT decodedJWT = verifier.verify(request.getRefreshToken());
            String email = decodedJWT.getSubject();

            Account account = accountRepository.findByEmail(email).orElseThrow(()->{
                throw new AccountNotFoundException();
            });

            String accessToken = JWT.create()
                    .withSubject(account.getEmail())
                    .withExpiresAt(
                            Instant.now().plus(
                                    jwtProperties.getAccessTokenExpirationAfterDays(),
                                    ChronoUnit.DAYS
                            )
                    )
                    .withClaim(
                            jwtProperties.getClaim(),
                            account.getAccountRole().getPermissions().stream()
                                    .map(AccountPermission::getPermission)
                                    .collect(Collectors.toList())
                    ).sign(secretKeyAccessToken);

            return new AuthenticationResponse(accessToken,request.getRefreshToken());

        }catch(Exception e){
            throw new AuthenticationFailedException();
        }

    }
}
