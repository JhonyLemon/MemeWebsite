package pl.jhonylemon.memewebsite.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.service.account.admin.AdminAccountService;

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

    @GetMapping(path = ApiPaths.Account.ACCOUNT_GET_ROLE)
    public ResponseEntity<AccountRoleGetDto> getAccountPermission(@PathVariable Long id){
        return ResponseEntity.ok().body(accountService.getAccountRole(id));
    }
    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_ROLE)
    public ResponseEntity<AccountRoleGetDto> changeAccountPermission(@PathVariable Long id, @PathVariable Long newRoleId){
        return ResponseEntity.ok().body(accountService.changeAccountRole(id,newRoleId));
    }
}
