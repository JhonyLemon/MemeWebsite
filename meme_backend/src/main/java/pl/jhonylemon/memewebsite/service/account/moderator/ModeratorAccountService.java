package pl.jhonylemon.memewebsite.service.account.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ModeratorAccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final ProfilePictureRepository profilePictureRepository;

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
