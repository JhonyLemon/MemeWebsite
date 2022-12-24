package pl.jhonylemon.memewebsite.controller.moderator;

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
import pl.jhonylemon.memewebsite.entity.Account;
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
class ModeratorAccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Autowired
    public AccountRepository accountRepository;
    @Autowired
    public ProfilePhotoRepository profilePhotoRepository;
    @Autowired
    public AccountRoleRepository accountRoleRepository;

    @BeforeAll
    void beforeAll() {
        objectMapper = new ObjectMapper();
    }

    @WithMockUser(authorities = {
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE"
    })
    @Test
    void updateAccountProfilePictureTest_Success() throws Exception {
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
                        .put(ApiPaths.Moderator.MODERATOR_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_UPDATE_PROFILE_PHOTO, account.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @WithMockUser(authorities = {
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE"
    })
    @Test
    void updateAccountNameTest_Success() throws Exception {
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
                        .put(ApiPaths.Moderator.MODERATOR_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_UPDATE_NAME, account.getId(),"Gacek1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE"
    })
    @Test
    void banAccountTest_Success() throws Exception {
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
                        .put(ApiPaths.Moderator.MODERATOR_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_BAN, account.getId(),true)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
