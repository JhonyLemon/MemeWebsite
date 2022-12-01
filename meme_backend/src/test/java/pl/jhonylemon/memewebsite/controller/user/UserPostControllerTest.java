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
import pl.jhonylemon.memewebsite.dto.post.PostPostDto;
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
    PostObjectRepository postObjectRepository;
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
    @Transactional
    void createPostTest_Success() throws Exception {

        Account account = Account.builder()
                .profilePicture(profilePictureRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        PostPostDto postPostDto = new PostPostDto();

        postPostDto.setTitle("gggg");
        postPostDto.setTags(new ArrayList<>());
        postPostDto.setVisible(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApiPaths.User.USER_PATH + ApiPaths.Post.POST_PATH +
                                ApiPaths.Post.POST_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postPostDto))
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
    @Transactional
    void deletePostSelfTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePictureRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        Post post = Post.builder()
                .account(account)
                .title("HEHEHE")
                .tags(new ArrayList<>())
                .creationDate(LocalDate.now())
                .isVisible(true)
                .isPublished(true)
                .files(List.of(postObjectRepository.findById(1L).orElse(null)))
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
    @Transactional
    void updatePostSelfTest_Success() throws Exception {
        Account account = Account.builder()
                .profilePicture(profilePictureRepository.findByDefaultProfileTrue().orElse(null))
                .password("123456789")
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .creationDate(LocalDate.now())
                .build();

        accountRepository.save(account);

        Post post = Post.builder()
                .account(account)
                .title("HEHEHE")
                .tags(new ArrayList<>())
                .creationDate(LocalDate.now())
                .isVisible(true)
                .isPublished(true)
                .files(List.of(postObjectRepository.findById(1L).orElse(null)))
                .build();

        postRepository.save(post);


        PostPutDto postPutDto = new PostPutDto();
        postPutDto.setTitle("hfhfh");
        postPutDto.setOrder(Map.of(
                1L,1L,
                2L,2L
        ));
        postPutDto.setVisible(true);
        postPutDto.setTags(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders
                        .put(ApiPaths.User.USER_PATH + ApiPaths.Post.POST_PATH +
                                ApiPaths.Post.POST_UPDATE, post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postPutDto))
                )
                .andExpect(status().isOk());
    }

}
