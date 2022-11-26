package pl.jhonylemon.memewebsite.controller.guest;

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
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class GuestAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    public AccountRepository accountRepository;
    @Autowired
    public ProfilePictureRepository profilePictureRepository;

    @Autowired
    public AccountPermissionRepository accountPermissionRepository;

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
    void createAccountTest_Success() throws Exception {

        AccountPostDto accountPostDto = new AccountPostDto();
        accountPostDto.setEmail("Gacek@gmail.com");
        accountPostDto.setPassword("1234567890");
        accountPostDto.setName("Gacek");
        accountPostDto.setProfilePictureId(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApiPaths.Guest.GUEST_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_CREATE)
                        .content(objectMapper.writeValueAsString(accountPostDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE"
    })
    void getAllAccountsTest_Success() throws Exception {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setDefaultPagingAndSorting();
        accountRequestDto.setFilters(new AccountFilterDto());
        accountRequestDto.getFilters().setEnabled(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApiPaths.Guest.GUEST_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_GET_ALL_PAGINATED)
                        .content(objectMapper.writeValueAsString(accountRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = {
            "ADMIN_ADD",
            "ADMIN_READ",
            "ADMIN_EDIT",
            "ADMIN_DELETE"
    })
    void getAccountTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePictureRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(ApiPaths.Guest.GUEST_PATH + ApiPaths.Account.ACCOUNT_PATH +
                                ApiPaths.Account.ACCOUNT_GET,1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}
