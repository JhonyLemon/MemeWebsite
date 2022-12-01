package pl.jhonylemon.memewebsite.service.account.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.accountrole.AccountRoleNotFoundException;
import pl.jhonylemon.memewebsite.exception.authorization.AuthorizationFailedException;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.mapper.AccountPermissionMapper;
import pl.jhonylemon.memewebsite.mapper.AccountRoleMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;
import pl.jhonylemon.memewebsite.service.account.guest.GuestAccountService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

    private final AccountMapper accountMapper;
    private final AccountPermissionMapper accountPermissionMapper;
    private final AccountRoleMapper accountRoleMapper;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRoleRepository accountRoleRepository;
    private final GuestAccountService guestAccountService;

    @Transactional
    public AccountGetFullDto updateAccountPassword(Long id,AccountPutPasswordDto accountPutPasswordDto) {
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
        return accountMapper.accountToFullGetDto(account);
    }

    @Transactional
    public void deleteAccount(Long id) {
        if(id==null || id<1){
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

        if (authAccount.getId().equals(account.getId())) {
            throw new AuthorizationFailedException();
        }
        accountRepository.delete(account);
    }

    public AccountGetFullDto getAccount(String email) {
        if(email==null){
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        return accountMapper.accountToFullGetDto(account);
    }

    public AccountGetFullDto getAccount(Long id) {
        if(id==null || id<1){
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
}
