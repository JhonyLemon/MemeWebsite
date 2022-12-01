package pl.jhonylemon.memewebsite.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRolePostDto;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRolePutDto;
import pl.jhonylemon.memewebsite.mapper.AccountPermissionMapper;
import pl.jhonylemon.memewebsite.service.accountrole.admin.AdminAccountRoleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Admin.ADMIN_PATH+ApiPaths.AccountRole.ACCOUNT_ROLE_PATH)
@CrossOrigin
public class AdminAccountRoleController {

    private final AdminAccountRoleService accountRoleService;
    private final AccountPermissionMapper accountPermissionMapper;

    @GetMapping(path = ApiPaths.AccountRole.ACCOUNT_ROLE_GET)
    public ResponseEntity<AccountRoleGetDto> getRole(@PathVariable Long id){
        return ResponseEntity.ok().body(accountRoleService.getRole(id));
    }
    @GetMapping(path = ApiPaths.AccountRole.ACCOUNT_ROLE_GET_ALL)
    public ResponseEntity<List<AccountRoleGetDto>> getRoles(){
        return ResponseEntity.ok().body(accountRoleService.getRoles());
    }

    @PostMapping(path = ApiPaths.AccountRole.ACCOUNT_ROLE_POST)
    public ResponseEntity<AccountRoleGetDto> postRole(@RequestBody AccountRolePostDto accountRolePostDto){
        return ResponseEntity.ok().body(accountRoleService.postRole(accountRolePostDto));
    }
    @PutMapping(path = ApiPaths.AccountRole.ACCOUNT_ROLE_PUT)
    public ResponseEntity<AccountRoleGetDto> putRole(@PathVariable Long id,@RequestBody AccountRolePutDto accountRolePutDto){
        return ResponseEntity.ok().body(accountRoleService.putRole(id,accountRolePutDto));
    }
    @PutMapping(path = ApiPaths.AccountRole.ACCOUNT_ROLE_PUT_DEFAULT)
    public ResponseEntity<AccountRoleGetDto> putDefaultRole(@PathVariable Long id){
        return ResponseEntity.ok().body(accountRoleService.putDefaultRole(id));
    }
    @DeleteMapping(path = ApiPaths.AccountRole.ACCOUNT_ROLE_DELETE)
    public ResponseEntity<Void> deleteRole(@PathVariable Long id){
        accountRoleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }







}
