package pl.jhonylemon.memewebsite.service.accountpermission.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionGetDto;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.exception.account.AccountInvalidParamException;
import pl.jhonylemon.memewebsite.exception.accountpermission.AccountPermissionNotFoundException;
import pl.jhonylemon.memewebsite.mapper.AccountPermissionMapper;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAccountPermissionService {

    private final AccountPermissionMapper accountPermissionMapper;
    private final AccountPermissionRepository accountPermissionRepository;

    public AccountPermissionGetDto getPermission(Long id){
        if (id == null || id<1) {
            throw new AccountInvalidParamException();
        }
        AccountPermission accountPermission = accountPermissionRepository.findById(id).orElseThrow(()->{
            throw new AccountPermissionNotFoundException();
        });
        return accountPermissionMapper.accountPermissionToGetDto(accountPermission);
    }
    public List<AccountPermissionGetDto> getPermissions(){
        return accountPermissionRepository.findAll().stream()
                .map(accountPermissionMapper::accountPermissionToGetDto)
                .collect(Collectors.toList());
    }


}

