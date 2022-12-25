package pl.jhonylemon.memewebsite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
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
import pl.jhonylemon.memewebsite.api.CommentApi;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.comment.CommentPostDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Comment;
import pl.jhonylemon.memewebsite.mapper.CommentMapper;
import pl.jhonylemon.memewebsite.model.CommentGetModelApi;
import pl.jhonylemon.memewebsite.model.CommentPostModelApi;
import pl.jhonylemon.memewebsite.repository.*;
import pl.jhonylemon.memewebsite.service.comment.CommentService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfilePhotoRepository profilePhotoRepository;
    @Autowired
    AccountPermissionRepository accountPermissionRepository;
    @Autowired
    public AccountRoleRepository accountRoleRepository;
    @Autowired
    public PostRepository postRepository;
    @Autowired
    public CommentRepository commentRepository;

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
    void deleteCommentTest_Success() throws Exception {
        Comment comment = Comment.builder()
                .post(postRepository.findById(1L).orElse(null))
                .comment("Heh")
                .account(accountRepository.findById(1L).orElse(null))
                .commentStatistics(new ArrayList<>())
                .build();

        commentRepository.save(comment);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete( ApiPaths.Version.v1 + ApiPaths.Comment.COMMENT_PATH + ApiPaths.Comment.COMMENT_ID, comment.getId())
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
    void replyTest_Success() throws Exception {

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

        CommentPostModelApi commentPostModelApi = new CommentPostModelApi();
        commentPostModelApi.setComment("Lubie pociągi");
        commentPostModelApi.setCommentId(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .post( ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Comment.COMMENT_ID + ApiPaths.Comment.COMMENT_PATH ,1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentPostModelApi))
                )
                .andExpect(status().isOk());
    }
}