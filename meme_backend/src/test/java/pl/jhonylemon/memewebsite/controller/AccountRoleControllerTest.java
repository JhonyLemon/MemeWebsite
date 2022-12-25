package pl.jhonylemon.memewebsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.api.AccountRoleApi;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountRole;
import pl.jhonylemon.memewebsite.mapper.AccountRoleMapper;
import pl.jhonylemon.memewebsite.model.AccountRoleGetModelApi;
import pl.jhonylemon.memewebsite.model.AccountRolePostModelApi;
import pl.jhonylemon.memewebsite.model.AccountRolePutModelApi;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePhotoRepository;
import pl.jhonylemon.memewebsite.service.accountrole.AccountRoleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class AccountRoleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    public AccountRepository accountRepository;
    @Autowired
    public ProfilePhotoRepository profilePhotoRepository;

    @Autowired
    public AccountPermissionRepository accountPermissionRepository;

    @Autowired
    public AccountRoleRepository accountRoleRepository;

    ObjectMapper objectMapper;

    @BeforeAll
    void beforeAll() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE",
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE",
            "USER_ADD",
            "USER_READ",
            "USER_EDIT",
            "USER_DELETE"
    },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void deleteRoleTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        AccountRole accountRole = AccountRole.builder()
                .role("gggg")
                .permissions(List.of())
                .accounts(new ArrayList<>())
                .isDefaultRole(false)
                .build();

        accountRoleRepository.save(accountRole);

        accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(
                                ApiPaths.Version.v1 + ApiPaths.AccountRole.ACCOUNT_ROLE_PATH+ ApiPaths.AccountRole.ACCOUNT_ROLE_ID,
                                accountRole.getId()
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE",
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE",
            "USER_ADD",
            "USER_READ",
            "USER_EDIT",
            "USER_DELETE"
    },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void getRoleTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(
                                ApiPaths.Version.v1 + ApiPaths.AccountRole.ACCOUNT_ROLE_PATH+ ApiPaths.AccountRole.ACCOUNT_ROLE_ID,
                                1L
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE",
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE",
            "USER_ADD",
            "USER_READ",
            "USER_EDIT",
            "USER_DELETE"
    },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void getRolesTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(
                                ApiPaths.Version.v1 + ApiPaths.AccountRole.ACCOUNT_ROLE_PATH+ ApiPaths.AccountRole.ACCOUNT_ROLE_GET_ALL,
                                1L
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE",
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE",
            "USER_ADD",
            "USER_READ",
            "USER_EDIT",
            "USER_DELETE"
    },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void postRoleTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        AccountRolePostModelApi postModelApi = new AccountRolePostModelApi();
        postModelApi.setRole("jgg");
        postModelApi.setPermissions(List.of(1L));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(
                                ApiPaths.Version.v1 + ApiPaths.AccountRole.ACCOUNT_ROLE_PATH,
                                1L
                        )
                        .content(objectMapper.writeValueAsString(postModelApi))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE",
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE",
            "USER_ADD",
            "USER_READ",
            "USER_EDIT",
            "USER_DELETE"
    },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void putDefaultRoleTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);


        mockMvc.perform(MockMvcRequestBuilders
                        .put(
                                ApiPaths.Version.v1 + ApiPaths.AccountRole.ACCOUNT_ROLE_PATH + ApiPaths.AccountRole.ACCOUNT_ROLE_ID + ApiPaths.AccountRole.ACCOUNT_ROLE_PUT_DEFAULT,
                                1L,
                                true
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE",
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE",
            "USER_ADD",
            "USER_READ",
            "USER_EDIT",
            "USER_DELETE"
    },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void putRoleTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        AccountRolePutModelApi accountRolePutModelApi = new AccountRolePutModelApi();
        accountRolePutModelApi.setRole("ttotgp");
        accountRolePutModelApi.setPermissions(List.of(1L,2L,3L,4L));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(
                                ApiPaths.Version.v1 + ApiPaths.AccountRole.ACCOUNT_ROLE_PATH + ApiPaths.AccountRole.ACCOUNT_ROLE_ID,
                                1L
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRolePutModelApi))
                )
                .andExpect(status().isOk());
    }
}