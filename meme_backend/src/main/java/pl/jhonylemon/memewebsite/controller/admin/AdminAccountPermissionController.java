package pl.jhonylemon.memewebsite.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionGetDto;
import pl.jhonylemon.memewebsite.mapper.AccountPermissionMapper;
import pl.jhonylemon.memewebsite.service.accountpermission.admin.AdminAccountPermissionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Admin.ADMIN_PATH+ApiPaths.AccountPermission.ACCOUNT_PERMISSION_PATH)
@CrossOrigin
public class AdminAccountPermissionController {

    private final AdminAccountPermissionService accountPermissionService;
    private final AccountPermissionMapper accountPermissionMapper;


    @GetMapping(path = ApiPaths.AccountPermission.ACCOUNT_PERMISSION_GET)
    public ResponseEntity<AccountPermissionGetDto> getPermission(@PathVariable Long id){
        return ResponseEntity.ok().body(accountPermissionService.getPermission(id));
    }
    @GetMapping(path = ApiPaths.AccountPermission.ACCOUNT_PERMISSION_GET_ALL_PAGINATED)
    public ResponseEntity<List<AccountPermissionGetDto>> getPermissions(){
        return ResponseEntity.ok().body(accountPermissionService.getPermissions());
    }

}
