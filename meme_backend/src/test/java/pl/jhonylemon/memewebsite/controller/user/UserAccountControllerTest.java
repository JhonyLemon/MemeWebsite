package pl.jhonylemon.memewebsite.controller.user;

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
import org.springframework.security.crypto.password.PasswordEncoder;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class UserAccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfilePhotoRepository profilePhotoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountPermissionRepository accountPermissionRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;


    @BeforeAll
    void beforeAll() {
        objectMapper = new ObjectMapper();
    }

    @WithMockUser(
            authorities = {
                    "USER_ADD",
                    "USER_READ",
                    "USER_EDIT",
                    "USER_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    @Test
    void deleteAccountSelfTest_Success() throws Exception {
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
                        .delete(ApiPaths.User.USER_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_DELETE,account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @WithMockUser(
            authorities = {
                    "USER_ADD",
                    "USER_READ",
                    "USER_EDIT",
                    "USER_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    @Test
    void updateAccountProfilePictureSelfTest_Success() throws Exception {
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
                        .put(ApiPaths.User.USER_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_UPDATE_PROFILE_PHOTO,account.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @WithMockUser(
            authorities = {
                    "USER_ADD",
                    "USER_READ",
                    "USER_EDIT",
                    "USER_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    @Test
    void updateAccountPasswordSelfTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePhotoRepository.findByDefaultProfileTrue().orElse(null))
                .password(passwordEncoder.encode("123456789"))
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
        accountPutPasswordDto.setOldPassword("123456789");

        mockMvc.perform(MockMvcRequestBuilders
                        .put(ApiPaths.User.USER_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_UPDATE_PASSWORD,account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountPutPasswordDto))
                )
                .andExpect(status().isOk());
    }
    @WithMockUser(
            authorities = {
                    "USER_ADD",
                    "USER_READ",
                    "USER_EDIT",
                    "USER_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    @Test
    void updateAccountNameSelfTest_Success() throws Exception {
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
                        .put(ApiPaths.User.USER_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_UPDATE_NAME,account.getId(),"Gacek1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @WithMockUser(
            authorities = {
                    "USER_ADD",
                    "USER_READ",
                    "USER_EDIT",
                    "USER_DELETE"
            },
            username = "Gacek@gmail.com",
            password = "123456789"
    )
    @Test
    void getSelfTest_Success() throws Exception {
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
                        .get(ApiPaths.User.USER_PATH + ApiPaths.Account.ACCOUNT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}
