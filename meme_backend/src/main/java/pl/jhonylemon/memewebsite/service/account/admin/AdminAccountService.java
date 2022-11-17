package pl.jhonylemon.memewebsite.service.account.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionGetDto;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionPostDto;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionPutDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.accountpermission.AccountPermissionNotFoundException;
import pl.jhonylemon.memewebsite.exception.authorization.AuthorizationFailedException;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.mapper.AccountPermissionMapper;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.service.account.guest.GuestAccountService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

    private final AccountMapper accountMapper;
    private final AccountPermissionMapper accountPermissionMapper;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountPermissionRepository accountPermissionRepository;
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
                (String) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
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

    public List<AccountPermissionGetDto> getAccountPermission(Long id) {
        if(id==null || id<1){
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
                    throw new AccountNotFoundException();
                });

        return account.getPermissions()
                .stream()
                .map(accountPermissionMapper::accountPermissionToGetDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AccountPermissionGetDto> changeAccountPermission(Long id,AccountPermissionPutDto accountPermissionPutDto) {
        if(id==null || id<1){
            throw new AccountInvalidParamException();
        }
        if(accountPermissionPutDto==null){
            throw new AccountInvalidParamException();
        }
        if(accountPermissionPutDto.getNewPermissionId()==null || accountPermissionPutDto.getNewPermissionId()<1){
            throw new AccountInvalidParamException();
        }
        if(accountPermissionPutDto.getOldPermissionId()==null || accountPermissionPutDto.getOldPermissionId()<1){
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        AccountPermission oldAccountPermission = accountPermissionRepository
                .findById(accountPermissionPutDto.getOldPermissionId())
                .orElseThrow(()->{throw new AccountPermissionNotFoundException();
                });
        oldAccountPermission.getAccounts().remove(account);
        AccountPermission newAccountPermission = accountPermissionRepository
                .findById(accountPermissionPutDto.getNewPermissionId())
                .orElseThrow(()->{throw new AccountPermissionNotFoundException();
                });
        newAccountPermission.getAccounts().add(account);

        account.getPermissions().remove(oldAccountPermission);
        account.getPermissions().add(newAccountPermission);

        return account.getPermissions().stream()
                .map(accountPermissionMapper::accountPermissionToGetDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AccountPermissionGetDto> addAccountPermission(Long id,AccountPermissionPostDto accountPermissionPutDto) {
        if(accountPermissionPutDto==null){
            throw new AccountInvalidParamException();
        }
        if(id==null || id<1){
            throw new AccountInvalidParamException();
        }
        if(accountPermissionPutDto.getPermissionId()==null || accountPermissionPutDto.getPermissionId()<1){
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        AccountPermission newAccountPermission = accountPermissionRepository
                .findById(accountPermissionPutDto.getPermissionId())
                .orElseThrow(()->{throw new AccountPermissionNotFoundException();
                });
        account.getPermissions().add(newAccountPermission);
        return account.getPermissions().stream()
                .map(accountPermissionMapper::accountPermissionToGetDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<AccountPermissionGetDto> deleteAccountPermission(Long id,AccountPermissionPostDto accountPermissionPutDto) {
        if(accountPermissionPutDto==null){
            throw new AccountInvalidParamException();
        }
        if(id==null || id<1){
            throw new AccountInvalidParamException();
        }
        if(accountPermissionPutDto.getPermissionId()==null || accountPermissionPutDto.getPermissionId()<1){
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        AccountPermission accountPermission = accountPermissionRepository
                .findById(accountPermissionPutDto.getPermissionId())
                .orElseThrow(()->{throw new AccountPermissionNotFoundException();
                });
        accountPermission.getAccounts().remove(account);
        account.getPermissions().remove(accountPermission);
        return account.getPermissions().stream()
                .map(accountPermissionMapper::accountPermissionToGetDto)
                .collect(Collectors.toList());
    }

}
