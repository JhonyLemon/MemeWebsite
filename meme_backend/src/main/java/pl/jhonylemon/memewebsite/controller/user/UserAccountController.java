package pl.jhonylemon.memewebsite.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.account.AccountPutPasswordDto;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.service.account.user.UserAccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = ApiPaths.User.USER_PATH+ApiPaths.Account.ACCOUNT_PATH)
public class UserAccountController {

    private final UserAccountService accountService;
    private final AccountMapper accountMapper;

    @DeleteMapping(path = ApiPaths.Account.ACCOUNT_DELETE)
    public ResponseEntity<Void> deleteAccountSelf(@PathVariable Long id){
        accountService.deleteAccountSelf(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_PROFILE_PHOTO)
    public ResponseEntity<AccountGetFullDto> updateAccountProfilePictureSelf(@PathVariable Long id,@PathVariable Long photoId){
        return ResponseEntity.ok().body(accountService.updateAccountProfilePictureSelf(id,photoId));
    }
    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_PASSWORD)
    public ResponseEntity<Void> updateAccountPasswordSelf(@PathVariable Long id, @RequestBody AccountPutPasswordDto accountPutPasswordDto){
        accountService.updateAccountPasswordSelf(id,accountPutPasswordDto);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_NAME)
    public ResponseEntity<AccountGetFullDto> updateAccountNameSelf(@PathVariable Long id,@PathVariable String name){
        return ResponseEntity.ok().body(accountService.updateAccountNameSelf(id,name));
    }
    @GetMapping()
    public ResponseEntity<AccountGetFullDto> getSelf(){
        return ResponseEntity.ok().body(accountService.getSelf());
    }

}
