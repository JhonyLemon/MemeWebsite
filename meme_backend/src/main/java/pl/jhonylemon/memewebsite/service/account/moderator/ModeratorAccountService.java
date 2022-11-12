package pl.jhonylemon.memewebsite.service.account.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;
import pl.jhonylemon.memewebsite.service.account.util.AccountUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static pl.jhonylemon.memewebsite.service.account.util.AccountUtil.validateRequest;

@Service
@RequiredArgsConstructor
public class ModeratorAccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
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
    public AccountGetFullDto updateAccountProfilePicture(Long id,Long profilePictureId) {
        if (id == null || id<1) {
            throw new AccountInvalidParamException();
        }
        if (profilePictureId == null || profilePictureId<1) {
            throw new AccountInvalidParamException();
        }

        Account account = accountRepository.findById(id).orElseThrow(() -> {
            throw new AccountNotFoundException();
        });

        ProfilePicture profilePicture = profilePictureRepository
                .findById(profilePictureId)
                .orElseThrow(()->{
                    throw new ProfilePictureNotFoundException();
                });

        account.getProfilePicture().getAccounts().remove(account);
        account.setProfilePicture(profilePicture);

        return accountMapper.accountToFullGetDto(account);
    }
}
