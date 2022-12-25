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
import pl.jhonylemon.memewebsite.api.PostStatisticApi;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.model.PostStatisticGetModelApi;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePhotoRepository;
import pl.jhonylemon.memewebsite.service.poststatistic.PostStatisticService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class PostStatisticControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    public AccountRepository accountRepository;
    @Autowired
    public ProfilePhotoRepository profilePhotoRepository;

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
    void getPostStatisticTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID + ApiPaths.PostStatistic.POST_STATISTIC_PATH , 1L)
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
                        .put( ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID + ApiPaths.PostStatistic.POST_STATISTIC_PATH + ApiPaths.PostStatistic.POST_STATISTIC_SET_FAVORITE,1L,true)
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
                        .put( ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID + ApiPaths.PostStatistic.POST_STATISTIC_PATH + ApiPaths.PostStatistic.POST_STATISTIC_SET_VOTE,1L,true)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
