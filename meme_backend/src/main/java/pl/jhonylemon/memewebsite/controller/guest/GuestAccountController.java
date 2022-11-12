package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.account.AccountGetShortDto;
import pl.jhonylemon.memewebsite.dto.account.AccountPostDto;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.service.account.guest.GuestAccountService;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH +ApiPaths.Account.ACCOUNT_PATH)
public class GuestAccountController{

    private final GuestAccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping(path = ApiPaths.Account.ACCOUNT_CREATE)
    public ResponseEntity<AccountGetFullDto> createAccount(@RequestBody AccountPostDto accountPostDto) {
        return ResponseEntity.ok().body(accountService.createAccount(accountPostDto));
    }

    @GetMapping(path = ApiPaths.Account.ACCOUNT_GET)
    public ResponseEntity<AccountGetShortDto> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok().body(accountService.getAccount(id));
    }

}
