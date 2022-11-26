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
import pl.jhonylemon.memewebsite.dto.comment.CommentPostDto;
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
class UserCommentControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfilePictureRepository profilePictureRepository;
    @Autowired
    AccountPermissionRepository accountPermissionRepository;

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
    void replyTest_Success() throws Exception {

        Account account = Account.builder()
                .profilePicture(profilePictureRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .permissions(accountPermissionRepository.findByDefaultPermissionTrue())
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        CommentPostDto commentPostDto = new CommentPostDto();
        commentPostDto.setComment("Lubie pociągi");
        commentPostDto.setAccountId(account.getId());

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApiPaths.User.USER_PATH + ApiPaths.Comment.COMMENT_PATH +
                                ApiPaths.Comment.COMMENT_REPLY,1L,1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentPostDto))
                )
                .andExpect(status().isOk());
    }

}