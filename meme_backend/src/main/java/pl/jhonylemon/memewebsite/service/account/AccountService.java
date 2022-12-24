package pl.jhonylemon.memewebsite.service.account;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationRefreshRequest;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationResponse;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.entity.AccountRole;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.exception.account.AccountEmailTakenException;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidPasswordException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.accountrole.AccountRoleNotFoundException;
import pl.jhonylemon.memewebsite.exception.authentication.AuthenticationFailedException;
import pl.jhonylemon.memewebsite.exception.authorization.AuthorizationFailedException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.mapper.AccountRoleMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePhotoRepository;
import pl.jhonylemon.memewebsite.security.component.JwtProperties;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;
import pl.jhonylemon.memewebsite.service.account.util.AccountUtil;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.jhonylemon.memewebsite.service.account.util.AccountUtil.validateRequest;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRoleRepository accountRoleRepository;
    private final ProfilePhotoRepository profilePhotoRepository;
    private final AccountRoleMapper accountRoleMapper;

    private final CustomUserDetailsService userDetailsService;
    @Qualifier("secretKeyAccessToken")
    @Autowired
    private Algorithm secretKeyAccessToken;
    @Qualifier("secretKeyRefreshToken")
    @Autowired
    private Algorithm secretKeyRefreshToken;
    private final JwtProperties jwtProperties;

    @Transactional
    public AccountGetFullDto banAccount(Long id, Boolean ban) {
        if (id==null || id<1 || ban==null) {
            throw new AccountInvalidParamException();
        }

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        account.setBanned(ban);
        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public AccountRoleGetDto changeAccountRole(Long id, Long newRoleId) {
        if(id==null || id<1){
            throw new AccountInvalidParamException();
        }
        if(newRoleId==null || newRoleId<1){
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    throw new AccountNotFoundException();
                });

        account.getAccountRole().getAccounts().remove(account);
        account.setAccountRole(accountRoleRepository.findById(newRoleId).orElseThrow(()->{
            throw new AccountRoleNotFoundException();
        }));

        return accountRoleMapper.accountRoleToGetDto(account.getAccountRole());
    }

    @Transactional
    public AccountGetFullDto createAccount(AccountPostDto accountPostDto) {
        if (!accountPostDto.isEmailValid()) {
            throw new AccountInvalidParamException();
        }
        if (!accountPostDto.isNameValid()) {
            throw new AccountInvalidParamException();
        }
        if (!accountPostDto.isPasswordValid()) {
            throw new AccountInvalidParamException();
        }
        if (accountRepository.isEmailTaken(accountPostDto.getEmail())) {
            throw new AccountEmailTakenException();
        }

        AccountRole role = accountRoleRepository.findByDefaultRoleTrue().orElseThrow(()->{
            throw new AccountRoleNotFoundException();
        });

        Account account = Account.builder()
                .name(accountPostDto.getName())
                .email(accountPostDto.getEmail())
                .creationDate(LocalDate.now())
                .password(passwordEncoder.encode(accountPostDto.getPassword()))
                .enabled(true)
                .banned(false)
                .accountRole(role)
                .profilePicture(
                        accountPostDto.getProfilePictureId() == null ?
                                profilePhotoRepository.findByDefaultProfileTrue().orElseThrow(() -> {
                                    throw new ProfilePictureNotFoundException();
                                }) :
                                profilePhotoRepository
                                        .findById(accountPostDto.getProfilePictureId()).orElseThrow(() -> {
                                            throw new ProfilePictureNotFoundException();
                                        })
                )
                .build();
        accountRepository.save(account);
        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public void deleteAccount(Long id) {
        if(id==null || id<1){
            throw new AccountInvalidParamException();
        }

        Account authAccount = userDetailsService.currentUser();

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        if (authAccount.getId().equals(account.getId())) {
            throw new AuthorizationFailedException();
        }
        accountRepository.delete(account);
    }

    @Transactional
    public void deleteAccountSelf() {
        Account account = userDetailsService.currentUser();
        if(accountRepository.isAccountAdmin(account.getId())) {
            throw new AuthorizationFailedException();
        }
        accountRepository.delete(account);
    }

    public AccountGetFullDto getAccount(Long id) {
        if (id == null || id < 1) {
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });
        return accountMapper.accountToFullGetDto(account);
    }

    public AccountRoleGetDto getAccountRole(Long id) {
        if(id==null || id<1){
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    throw new AccountNotFoundException();
                });

        return accountRoleMapper.accountRoleToGetDto(account.getAccountRole());
    }

    public AccountPageGetDto getAllAccounts(AccountRequestDto accountRequestDto) {
        validateRequest(accountRequestDto);

        Page<Account> accounts = accountRepository
                .findAll(AccountUtil.getSpecification(accountRequestDto.getFilters()),
                        AccountUtil.createPageRequest(accountRequestDto.getPagingAndSorting()));

        List<AccountGetFullDto> accountGetFullDtos = new ArrayList<>();

        accounts.forEach(a-> accountGetFullDtos.add(accountMapper.accountToFullGetDto(a)));

        return new AccountPageGetDto(
                accountGetFullDtos,
                accounts.getTotalPages(),
                accounts.getTotalElements(),
                accountRequestDto.getFilters());
    }

    public AccountGetFullDto getSelf() {
        return accountMapper.accountToFullGetDto(userDetailsService.currentUser());
    }

    public AuthenticationResponse refreshJWT(AuthenticationRefreshRequest request) {
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

    @Transactional
    public AccountGetFullDto updateAccountName(Long id, String name) {
        if (id==null || id<1 || name==null || name.isBlank()) {
            throw new AccountInvalidParamException();
        }

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        account.setName(name);
        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public AccountGetFullDto updateAccountNameSelf(String name) {
        if (name==null || name.isBlank()) {
            throw new AccountInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        account.setName(name);
        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public void updateAccountPassword(Long id, AccountPutPasswordDto accountPutPasswordDto) {
        if (id == null || id<1) {
            throw new AccountInvalidParamException();
        }
        if (!accountPutPasswordDto.isPasswordValid()) {
            throw new AccountInvalidParamException();
        }

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        account.setPassword(passwordEncoder.encode(accountPutPasswordDto.getNewPassword()));
    }

    @Transactional
    public void updateAccountPasswordSelf(AccountPutPasswordDto accountPutPasswordDto) {
        if (accountPutPasswordDto == null) {
            throw new AccountInvalidParamException();
        }
        if (!accountPutPasswordDto.isPasswordValid()) {
            throw new AccountInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        if (!passwordEncoder.matches(accountPutPasswordDto.getOldPassword(), account.getPassword())) {
            throw new AccountInvalidPasswordException();
        }
        account.setPassword("");
        account.setPassword(passwordEncoder.encode(accountPutPasswordDto.getNewPassword()));
    }

    @Transactional
    public AccountGetFullDto updateAccountProfilePicture(Long id, Long profilePictureId) {
        if (id == null || id<1) {
            throw new AccountInvalidParamException();
        }
        if (profilePictureId == null || profilePictureId<1) {
            throw new AccountInvalidParamException();
        }

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        ProfilePicture profilePicture = profilePhotoRepository
                .findById(profilePictureId)
                .orElseThrow(()->{
                    throw new ProfilePictureNotFoundException();
                });

        account.getProfilePicture().getAccounts().remove(account);
        account.setProfilePicture(profilePicture);

        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public AccountGetFullDto updateAccountProfilePictureSelf(Long profilePictureId) {
        if (profilePictureId == null || profilePictureId<1) {
            throw new AccountInvalidParamException();
        }
        Account account = userDetailsService.currentUser();
        ProfilePicture profilePicture = profilePhotoRepository
                .findById(profilePictureId)
                .orElseThrow(() -> {
                    throw new ProfilePictureNotFoundException();
                });
        account.getProfilePicture().getAccounts().remove(account);
        account.setProfilePicture(profilePicture);

        return accountMapper.accountToFullGetDto(account);
    }
}
