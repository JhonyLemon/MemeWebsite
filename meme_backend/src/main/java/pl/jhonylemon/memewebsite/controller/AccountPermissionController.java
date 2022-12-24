package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.AccountPermissionApi;
import pl.jhonylemon.memewebsite.mapper.AccountPermissionMapper;
import pl.jhonylemon.memewebsite.model.AccountPermissionGetModelApi;
import pl.jhonylemon.memewebsite.service.accountpermission.AccountPermissionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AccountPermissionController implements AccountPermissionApi {

    private final AccountPermissionService accountPermissionService;
    private final AccountPermissionMapper accountPermissionMapper;

    @Override
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<AccountPermissionGetModelApi> getPermission(Long id) {
        return ResponseEntity.ok().body(
                accountPermissionMapper.accountPermissionGetDtoToModelApi(
                        accountPermissionService.getPermission(id)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN_READ')")
    public ResponseEntity<List<AccountPermissionGetModelApi>> getPermissions() {
        return ResponseEntity.ok().body(
                accountPermissionService.getPermissions().stream()
                        .map(accountPermissionMapper::accountPermissionGetDtoToModelApi)
                        .collect(Collectors.toList())
        );
    }
}
