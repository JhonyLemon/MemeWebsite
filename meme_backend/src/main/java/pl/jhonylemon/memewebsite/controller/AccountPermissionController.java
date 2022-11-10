package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.mapper.AccountPermissionMapper;
import pl.jhonylemon.memewebsite.service.AccountPermissionService;

@RestController
@RequiredArgsConstructor
public class AccountPermissionController {

    private final AccountPermissionService accountPermissionService;
    private final AccountPermissionMapper accountPermissionMapper;

}
