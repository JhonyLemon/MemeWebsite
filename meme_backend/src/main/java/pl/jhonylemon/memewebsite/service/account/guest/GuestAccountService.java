package pl.jhonylemon.memewebsite.service.account.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.exception.account.AccountEmailTakenException;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;
import pl.jhonylemon.memewebsite.service.account.util.AccountUtil;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pl.jhonylemon.memewebsite.service.account.util.AccountUtil.validateRequest;

@Service
@RequiredArgsConstructor
public class GuestAccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountPermissionRepository accountPermissionRepository;
    private final ProfilePictureRepository profilePictureRepository;

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

        List<AccountPermission> permissions = accountPermissionRepository.findByDefaultPermissionTrue();

        Account account = Account.builder()
                .name(accountPostDto.getName())
                .email(accountPostDto.getEmail())
                .creationDate(LocalDate.now())
                .password(passwordEncoder.encode(accountPostDto.getPassword()))
                .enabled(true)
                .banned(false)
                .permissions(permissions)
                .profilePicture(
                        accountPostDto.getProfilePictureId() == null ?
                                profilePictureRepository.findByDefaultProfileTrue().orElseThrow(() -> {
                                    throw new ProfilePictureNotFoundException();
                                }) :
                                profilePictureRepository
                                        .findById(accountPostDto.getProfilePictureId()).orElseThrow(() -> {
                                            throw new ProfilePictureNotFoundException();
                                        })
                )
                .build();
        accountRepository.save(account);
        return accountMapper.accountToFullGetDto(account);
    }

    public AccountGetShortDto getAccount(Long id) {
        if (id == null || id < 1) {
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });
        return accountMapper.accountToShortGetDto(account);
    }

    public AccountGetShortDto getAccount(String email) {
        if (email == null) {
            throw new AccountInvalidParamException();
        }
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });
        return accountMapper.accountToShortGetDto(account);
    }

}
