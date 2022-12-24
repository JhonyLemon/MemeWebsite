package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.AccountApi;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.mapper.AccountRoleMapper;
import pl.jhonylemon.memewebsite.model.*;
import pl.jhonylemon.memewebsite.service.account.AccountService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AccountController implements AccountApi {

    private final AccountMapper accountMapper;
    private final AccountService accountService;
    private final AccountRoleMapper accountRoleMapper;

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<AccountGetFullModelApi> banAccount(Long id, Boolean ban) {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.banAccount(id, ban)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_EDIT')")
    public ResponseEntity<AccountRoleGetModelApi> changeAccountRole(Long id, Long newRoleId) {
        return ResponseEntity.ok().body(
                accountRoleMapper.accountRoleGetDtoToModelApi(
                        accountService.changeAccountRole(id, newRoleId)
                )
        );
    }

    @Override
    public ResponseEntity<AccountGetFullModelApi> createAccount(AccountPostModelApi accountPostModelApi) {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.createAccount(
                                accountMapper.accountPostModelApiToDto(accountPostModelApi)
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_DELETE')")
    public ResponseEntity<Void> deleteAccount(Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResponseEntity<Void> deleteAccountSelf() {
        accountService.deleteAccountSelf();
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<AccountGetFullModelApi> getAccount(Long id) {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.getAccount(id)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<AccountRoleGetModelApi> getAccountRole(Long id) {
        return ResponseEntity.ok().body(
                accountRoleMapper.accountRoleGetDtoToModelApi(
                        accountService.getAccountRole(id)
                )
        );
    }

    @Override
    public ResponseEntity<AccountPageGetModelApi> getAllAccounts(AccountRequestModelApi accountRequestModelApi) {
        return ResponseEntity.ok().body(
                accountMapper.accountPageDtoToModelApi(
                        accountService.getAllAccounts(
                                accountMapper.accountRequestModelApiToDto(accountRequestModelApi)
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<AccountGetFullModelApi> getSelf() {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.getSelf()
                )
        );
    }

    @Override
    public ResponseEntity<AuthenticationResponseModelApi> refreshJWT(AuthenticationRefreshRequestModelApi authenticationRefreshRequestModelApi) {
        return ResponseEntity.ok().body(
                accountMapper.authDtoToModelApi(
                        accountService.refreshJWT(
                                accountMapper.accountAuthModelApiToDto(authenticationRefreshRequestModelApi)
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<AccountGetFullModelApi> updateAccountName(Long id, String name) {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.updateAccountName(
                                id,
                                name
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public ResponseEntity<AccountGetFullModelApi> updateAccountNameSelf(String name) {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.updateAccountNameSelf(
                                name
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_EDIT')")
    public ResponseEntity<Void> updateAccountPassword(Long id, AccountPutPasswordAdminModelApi accountPutPasswordAdminModelApi) {
        accountService.updateAccountPassword(
                id,
                accountMapper.accountPutPasswordAdminModelApiToDto(accountPutPasswordAdminModelApi)
        );
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public ResponseEntity<Void> updateAccountPasswordSelf(AccountPutPasswordUserModelApi accountPutPasswordUserModelApi) {
        accountService.updateAccountPasswordSelf(
                accountMapper.accountPutPasswordUserModelApiToDto(accountPutPasswordUserModelApi)
        );
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<AccountGetFullModelApi> updateAccountProfilePicture(Long id, Long photoId) {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.updateAccountProfilePicture(
                                id,
                                photoId
                        )
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('USER_EDIT')")
    public ResponseEntity<AccountGetFullModelApi> updateAccountProfilePictureSelf(Long photoId) {
        return ResponseEntity.ok().body(
                accountMapper.accountFullDtoToModelApi(
                        accountService.updateAccountProfilePictureSelf(
                                photoId
                        )
                )
        );
    }
}
