package pl.jhonylemon.memewebsite.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.account.AccountPutPasswordDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePhotoRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class AdminAccountControllerTest {

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
            "ADMIN_DELETE"
    })
    void updateAccountPasswordTest_Success() throws Exception {

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

        AccountPutPasswordDto accountPutPasswordDto = new AccountPutPasswordDto();
        accountPutPasswordDto.setNewPassword("987654321");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_UPDATE_PASSWORD, account.getId())
                        .content(objectMapper.writeValueAsString(accountPutPasswordDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            authorities = {
                    "ADMIN_ADD",
                    "ADMIN_READ",
                    "ADMIN_EDIT",
                    "ADMIN_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void deleteAccountTest_Success() throws Exception {
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

        Account account1 = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("1234567890")
                .name("Gacek")
                .email("Gacek1@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_DELETE, account1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            authorities = {
                    "ADMIN_ADD",
                    "ADMIN_READ",
                    "ADMIN_EDIT",
                    "ADMIN_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void getAccountTest_Success() throws Exception {
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
                        .get( ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_GET, account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            authorities = {
                    "ADMIN_ADD",
                    "ADMIN_READ",
                    "ADMIN_EDIT",
                    "ADMIN_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void getAccountPermissionTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .creationDate(LocalDate.now())
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .build();

        accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .get( ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_GET_ROLE, account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            authorities = {
                    "ADMIN_ADD",
                    "ADMIN_READ",
                    "ADMIN_EDIT",
                    "ADMIN_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    void changeAccountRoleTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .creationDate(LocalDate.now())
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .build();

        accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_UPDATE_ROLE, account.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}
