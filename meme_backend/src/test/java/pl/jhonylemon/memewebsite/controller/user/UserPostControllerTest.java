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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.comment.CommentPostDto;
import pl.jhonylemon.memewebsite.dto.post.PostPutDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.repository.*;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class UserPostControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfilePictureRepository profilePictureRepository;
    @Autowired
    AccountPermissionRepository accountPermissionRepository;

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostFileRepository postFileRepository;

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
    void createPostTest_Success() throws Exception {

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

        MockMultipartFile photo = new MockMultipartFile(
                "files",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(
                        ResourceUtils
                                .getFile("classpath:photo/profile.png")
                ).readAllBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart(ApiPaths.User.USER_PATH + ApiPaths.Post.POST_PATH +
                        ApiPaths.Post.POST_CREATE,account.getId())
                        .file(photo)
                        .params(new LinkedMultiValueMap<>(Map.of(
                                "title", List.of("Hue"),
                                "descriptions",List.of("FajnyPost"),
                                "visible",List.of("true"),
                                "tags",List.of()
                        )))
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
    void deletePostSelfTest_Success() throws Exception {
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

        Post post = Post.builder()
                .account(account)
                .title("HEHEHE")
                .tags(new ArrayList<>())
                .creationDate(LocalDate.now())
                .visible(true)
                .files(List.of(postFileRepository.findById(1L).orElse(null)))
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ApiPaths.User.USER_PATH + ApiPaths.Post.POST_PATH +
                                ApiPaths.Post.POST_DELETE,post.getId())
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
    void updatePostSelfTest_Success() throws Exception {
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

        Post post = Post.builder()
                .account(account)
                .title("HEHEHE")
                .tags(new ArrayList<>())
                .creationDate(LocalDate.now())
                .visible(true)
                .files(List.of(postFileRepository.findById(1L).orElse(null)))
                .build();

        postRepository.save(post);


        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .multipart(ApiPaths.User.USER_PATH + ApiPaths.Post.POST_PATH +
                        ApiPaths.Post.POST_UPDATE,post.getId());

        builder.with(request -> {
            request.setMethod(HttpMethod.PUT.name());
            return request;
        });

        mockMvc.perform(builder
                .params(new LinkedMultiValueMap<>(Map.of(
                        "title", List.of("Hue")
                ))))
                .andExpect(status().isOk());
    }

}
