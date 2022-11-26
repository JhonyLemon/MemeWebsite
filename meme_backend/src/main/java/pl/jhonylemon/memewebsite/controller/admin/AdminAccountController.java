package pl.jhonylemon.memewebsite.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionGetDto;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionPutDto;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.service.account.admin.AdminAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Admin.ADMIN_PATH+ApiPaths.Account.ACCOUNT_PATH)
@CrossOrigin
public class AdminAccountController{

    private final AdminAccountService accountService;
    private final AccountMapper accountMapper;

    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_PASSWORD)
    public ResponseEntity<AccountGetFullDto> updateAccountPassword(@PathVariable Long id, @RequestBody AccountPutPasswordDto putPasswordDto){
        return ResponseEntity.ok().body(accountService.updateAccountPassword(id,putPasswordDto));
    }
    @DeleteMapping(path = ApiPaths.Account.ACCOUNT_DELETE)
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(path = ApiPaths.Account.ACCOUNT_GET)
    public ResponseEntity<AccountGetFullDto> getAccount(@PathVariable Long id){
        return ResponseEntity.ok().body(accountService.getAccount(id));
    }

    @GetMapping(path = ApiPaths.Account.ACCOUNT_GET_PERMISSION)
    public ResponseEntity<List<AccountPermissionGetDto>> getAccountPermission(@PathVariable Long id){
        return ResponseEntity.ok().body(accountService.getAccountPermission(id));
    }
    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_PERMISSION)
    public ResponseEntity<List<AccountPermissionGetDto>> changeAccountPermission(@PathVariable Long id, @RequestBody AccountPermissionPutDto accountPermissionPutDto){
        return ResponseEntity.ok().body(accountService.changeAccountPermission(id,accountPermissionPutDto));
    }
    @PostMapping(path = ApiPaths.Account.ACCOUNT_ADD_PERMISSION)
    public ResponseEntity<List<AccountPermissionGetDto>> addAccountPermission(@PathVariable Long id,@PathVariable Long permissionId){
        return ResponseEntity.ok().body(accountService.addAccountPermission(id,permissionId));
    }
    @DeleteMapping(path = ApiPaths.Account.ACCOUNT_DELETE_PERMISSION)
    public ResponseEntity<List<AccountPermissionGetDto>> deleteAccountPermission(@PathVariable Long id,@PathVariable Long permissionId){
        return ResponseEntity.ok().body(accountService.deleteAccountPermission(id,permissionId));
    }

}
