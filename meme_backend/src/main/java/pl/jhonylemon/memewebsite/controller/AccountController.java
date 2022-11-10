package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.AccountMapper;
import pl.jhonylemon.memewebsite.service.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

}
