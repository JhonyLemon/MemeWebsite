package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.AccountRoleMapper;
import pl.jhonylemon.memewebsite.service.AccountRoleService;

@RestController
@RequiredArgsConstructor
public class AccountRoleController {

    private final AccountRoleService accountRoleService;
    private final AccountRoleMapper accountRoleMapper;

}
