package pl.jhonylemon.memewebsite.service.accountrole.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRolePostDto;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRolePutDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.entity.AccountRole;
import pl.jhonylemon.memewebsite.exception.accountrole.AccountRoleDefaultRoleException;
import pl.jhonylemon.memewebsite.exception.accountrole.AccountRoleInvalidParamException;
import pl.jhonylemon.memewebsite.exception.accountrole.AccountRoleNotFoundException;
import pl.jhonylemon.memewebsite.exception.accountrole.AccountRoleUsedException;
import pl.jhonylemon.memewebsite.mapper.AccountRoleMapper;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAccountRoleService {

    private final AccountRoleMapper accountRoleMapper;
    private final AccountPermissionRepository accountPermissionRepository;
    private final AccountRoleRepository accountRoleRepository;


    public List<AccountRoleGetDto> getRoles() {
        return accountRoleRepository.findAll().stream()
                .map(accountRoleMapper::accountRoleToGetDto)
                .collect(Collectors.toList());
    }

    public AccountRoleGetDto getRole(Long id) {
        if (id==null || id<1) {
            throw new AccountRoleInvalidParamException();
        }
        return accountRoleMapper.accountRoleToGetDto(accountRoleRepository.findById(id).orElseThrow(()->{
            throw new AccountRoleNotFoundException();
        }));
    }

    @Transactional
    public AccountRoleGetDto postRole(AccountRolePostDto accountRolePostDto) {
        if(accountRolePostDto==null || accountRolePostDto.getPermissions() == null){
            throw new AccountRoleInvalidParamException();
        }
        if(accountRolePostDto.getRole() == null || accountRolePostDto.getRole().isEmpty()){
            throw new AccountRoleInvalidParamException();
        }
        if(accountRolePostDto.getPermissions().isEmpty()){
            throw new AccountRoleInvalidParamException();
        }
        List<AccountPermission> accountPermissions = accountPermissionRepository
                .findAllById(accountRolePostDto.getPermissions());

        if(accountPermissions.isEmpty()){
            throw new AccountRoleInvalidParamException();
        }

        AccountRole accountRole = AccountRole.builder()
                .role(accountRolePostDto.getRole())
                .isDefaultRole(false)
                .permissions(accountPermissions)
                .build();

        accountRoleRepository.save(accountRole);

        return accountRoleMapper.accountRoleToGetDto(accountRole);
    }

    @Transactional
    public AccountRoleGetDto putRole(Long id, AccountRolePutDto accountRolePutDto) {
        if (id==null || id<1) {
            throw new AccountRoleInvalidParamException();
        }
        if(accountRolePutDto==null || accountRolePutDto.getPermissions() == null){
            throw new AccountRoleInvalidParamException();
        }
        if(accountRolePutDto.getRole() == null || accountRolePutDto.getRole().isEmpty()){
            throw new AccountRoleInvalidParamException();
        }
        if(accountRolePutDto.getPermissions().isEmpty()){
            throw new AccountRoleInvalidParamException();
        }

        AccountRole accountRole = accountRoleRepository.findById(id).orElseThrow(()->{
            throw new AccountRoleNotFoundException();
        });

        List<AccountPermission> accountPermissions = accountPermissionRepository
                .findAllById(accountRolePutDto.getPermissions());

        if(accountPermissions.isEmpty()){
            throw new AccountRoleInvalidParamException();
        }

        accountRole.getPermissions().forEach(p-> p.getAccountRoles().remove(accountRole));
        accountRole.setPermissions(accountPermissions);
        accountRole.setRole(accountRolePutDto.getRole());

        return accountRoleMapper.accountRoleToGetDto(accountRole);
    }

    @Transactional
    public AccountRoleGetDto putDefaultRole(Long id) {
        if (id==null || id<1) {
            throw new AccountRoleInvalidParamException();
        }

        AccountRole accountRole = accountRoleRepository.findById(id).orElseThrow(()->{
            throw new AccountRoleNotFoundException();
        });

        AccountRole defaultRole = accountRoleRepository.findByDefaultRoleTrue().orElseThrow(()->{
            throw new AccountRoleNotFoundException();
        });

        defaultRole.isDefaultRole(false);
        accountRole.isDefaultRole(true);

        List<Account> accounts = defaultRole.getAccounts();
        defaultRole.getAccounts().clear();
        accounts.forEach(a->a.setAccountRole(accountRole));
        accountRole.setAccounts(accounts);

        return accountRoleMapper.accountRoleToGetDto(accountRole);
    }

    @Transactional
    public void deleteRole(Long id) {
        if (id==null || id<1) {
            throw new AccountRoleInvalidParamException();
        }
        AccountRole accountRole = accountRoleRepository.findById(id).orElseThrow(()->{
            throw new AccountRoleNotFoundException();
        });

        if(!accountRole.getAccounts().isEmpty()){
            throw new AccountRoleUsedException();
        }
        if(accountRole.isDefaultRole()){
            throw new AccountRoleDefaultRoleException();
        }

        accountRoleRepository.delete(accountRole);
    }

}

