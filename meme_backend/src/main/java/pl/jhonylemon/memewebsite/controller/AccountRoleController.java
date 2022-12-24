package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.AccountRoleApi;
import pl.jhonylemon.memewebsite.mapper.AccountRoleMapper;
import pl.jhonylemon.memewebsite.model.AccountRoleGetModelApi;
import pl.jhonylemon.memewebsite.model.AccountRolePostModelApi;
import pl.jhonylemon.memewebsite.model.AccountRolePutModelApi;
import pl.jhonylemon.memewebsite.service.accountrole.AccountRoleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AccountRoleController implements AccountRoleApi {

    private final AccountRoleService accountRoleService;
    private final AccountRoleMapper accountRoleMapper;

    @Override
    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    public ResponseEntity<Void> deleteRole(Long id) {
        accountRoleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<AccountRoleGetModelApi> getRole(Long id) {
        return ResponseEntity.ok().body(
                accountRoleMapper.accountRoleGetDtoToModelApi(
                        accountRoleService.getRole(id)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<List<AccountRoleGetModelApi>> getRoles() {
        return ResponseEntity.ok().body(
                accountRoleService.getRoles().stream()
                        .map(accountRoleMapper::accountRoleGetDtoToModelApi)
                        .collect(Collectors.toList())
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_ADD')")
    public ResponseEntity<AccountRoleGetModelApi> postRole(AccountRolePostModelApi accountRolePostModelApi) {
        return ResponseEntity.ok().body(
                accountRoleMapper.accountRoleGetDtoToModelApi(
                        accountRoleService.postRole(
                                accountRoleMapper.accountRolePostModelApiToDto(accountRolePostModelApi)
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_EDIT')")
    public ResponseEntity<AccountRoleGetModelApi> putDefaultRole(Long id) {
        return ResponseEntity.ok().body(
                accountRoleMapper.accountRoleGetDtoToModelApi(
                        accountRoleService.putDefaultRole(
                                id
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_EDIT')")
    public ResponseEntity<AccountRoleGetModelApi> putRole(Long id, AccountRolePutModelApi accountRolePutModelApi) {
        return ResponseEntity.ok().body(
                accountRoleMapper.accountRoleGetDtoToModelApi(
                        accountRoleService.putRole(
                                id,
                                accountRoleMapper.accountRolePutModelApiToDto(accountRolePutModelApi)
                        )
                )
        );
    }
}