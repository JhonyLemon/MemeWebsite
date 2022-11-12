package pl.jhonylemon.memewebsite.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.account.AccountPageGetDto;
import pl.jhonylemon.memewebsite.dto.account.AccountRequestDto;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.service.account.moderator.ModeratorAccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = ApiPaths.Moderator.MODERATOR_PATH+ApiPaths.Account.ACCOUNT_PATH)
public class ModeratorAccountController {

    private final ModeratorAccountService accountService;
    private final AccountMapper accountMapper;

    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_PROFILE_PHOTO)
    public ResponseEntity<AccountGetFullDto> updateAccountProfilePicture(@PathVariable Long id, @PathVariable Long photoId){
        return ResponseEntity.ok().body(accountService.updateAccountProfilePicture(id,photoId));
    }
    @PutMapping(path = ApiPaths.Account.ACCOUNT_UPDATE_NAME)
    public ResponseEntity<AccountGetFullDto> updateAccountName(@PathVariable Long id, @PathVariable String name){
        return ResponseEntity.ok().body(accountService.updateAccountName(id,name));
    }
    @PostMapping(path = ApiPaths.Account.ACCOUNT_GET_ALL_PAGINATED)
    public ResponseEntity<AccountPageGetDto> getAllAccounts(@RequestBody AccountRequestDto requestDto){
        return ResponseEntity.ok().body(accountService.getAllAccounts(requestDto));
    }

    @PutMapping(path = ApiPaths.Account.ACCOUNT_BAN)
    public ResponseEntity<AccountGetFullDto> banAccount(@PathVariable Long id, @PathVariable Boolean ban){
        return ResponseEntity.ok().body(accountService.banAccount(id,ban));
    }
}
