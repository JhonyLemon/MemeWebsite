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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePhotoRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class UserPostStatisticControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfilePhotoRepository profilePhotoRepository;
    @Autowired
    AccountPermissionRepository accountPermissionRepository;
    @Autowired
    public AccountRoleRepository accountRoleRepository;

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
    void setVoteStatisticTest_Success() throws Exception {
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
                        .post(ApiPaths.User.USER_PATH + ApiPaths.PostStatistic.POST_STATISTIC_PATH +
                                ApiPaths.PostStatistic.POST_STATISTIC_SET_VOTE,account.getId(),1L,true)
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
    void setFavoriteStatisticTest_Success() throws Exception {
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
                        .post(ApiPaths.User.USER_PATH + ApiPaths.PostStatistic.POST_STATISTIC_PATH +
                                ApiPaths.PostStatistic.POST_STATISTIC_SET_FAVORITE,account.getId(),1L,true)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}
