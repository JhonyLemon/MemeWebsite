package pl.jhonylemon.memewebsite.service.account.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidPasswordException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.authorization.AuthorizationFailedException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;
import pl.jhonylemon.memewebsite.service.account.guest.GuestAccountService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfilePictureRepository profilePictureRepository;

    private final GuestAccountService guestAccountService;

    public AccountGetFullDto getSelf() {
        return accountMapper
                .accountToFullGetDto(
                        accountRepository.findByEmail(
                                        ((User)SecurityContextHolder.getContext()
                                                .getAuthentication()
                                                .getPrincipal())
                                                .getUsername())
                                .orElseThrow(() -> {
                                    throw new AccountNotFoundException();
                                })
                );
    }

    @Transactional
    public AccountGetFullDto updateAccountNameSelf(Long id, String name) {
        if (id==null || id<1 || name==null || name.isBlank()) {
            throw new AccountInvalidParamException();
        }

        AccountGetShortDto authAccount = guestAccountService.getAccount(
                ((User)SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername()
        );

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        if (!authAccount.getId().equals(account.getId())) {
            throw new AuthorizationFailedException();
        }

        account.setName(name);
        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public void updateAccountPasswordSelf(Long id, AccountPutPasswordDto accountPutPasswordDto) {
        if (id == null || id<1 || accountPutPasswordDto == null) {
            throw new AccountInvalidParamException();
        }
        if (!accountPutPasswordDto.isPasswordValid()) {
            throw new AccountInvalidParamException();
        }
        AccountGetShortDto authAccount = guestAccountService.getAccount(
                ((User)SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername()
        );

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        if (!authAccount.getId().equals(account.getId())) {
            throw new AuthorizationFailedException();
        }

        if (!passwordEncoder.matches(accountPutPasswordDto.getOldPassword(), account.getPassword())) {
            throw new AccountInvalidPasswordException();
        }
        account.setPassword("");
        account.setPassword(passwordEncoder.encode(accountPutPasswordDto.getNewPassword()));
    }

    @Transactional
    public AccountGetFullDto updateAccountProfilePictureSelf(Long id,Long profilePictureId) {
        if (id == null || id<1) {
            throw new AccountInvalidParamException();
        }
        if (profilePictureId == null || profilePictureId<1) {
            throw new AccountInvalidParamException();
        }

        AccountGetShortDto authAccount = guestAccountService.getAccount(
                ((User)SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername()
        );

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        if (!authAccount.getId().equals(account.getId())) {
            throw new AuthorizationFailedException();
        }

        ProfilePicture profilePicture = profilePictureRepository
                .findById(profilePictureId)
                .orElseThrow(() -> {
                    throw new ProfilePictureNotFoundException();
                });

        account.getProfilePicture().getAccounts().remove(account);
        account.setProfilePicture(profilePicture);

        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public void deleteAccountSelf(Long id) {
        if (id == null || id < 1) {
            throw new AccountInvalidParamException();
        }

        AccountGetShortDto authAccount = guestAccountService.getAccount(
                ((User)SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername()
        );

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });
        if (!authAccount.getId().equals(account.getId())) {
            throw new AuthorizationFailedException();
        }

        if(accountRepository.isAccountAdmin(account.getId())) {
            throw new AuthorizationFailedException();
        }

        accountRepository.delete(account);
    }

}
