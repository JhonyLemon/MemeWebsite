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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.api.PostApi;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.post.PostPostDto;
import pl.jhonylemon.memewebsite.dto.post.PostPutDto;
import pl.jhonylemon.memewebsite.dto.post.PostRequestDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.mapper.PostMapper;
import pl.jhonylemon.memewebsite.model.*;
import pl.jhonylemon.memewebsite.repository.*;
import pl.jhonylemon.memewebsite.service.post.PostService;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ProfilePhotoRepository profilePhotoRepository;
    @Autowired
    AccountPermissionRepository accountPermissionRepository;

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostObjectRepository postObjectRepository;
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
    void createPublishedPostTest_Success() throws Exception {

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

        MockMultipartFile photo = new MockMultipartFile(
                "files",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(
                        ResourceUtils
                                .getFile("classpath:photo/profile.png")
                ).readAllBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(ApiPaths.Version.v2 + ApiPaths.Post.POST_PATH)
                        .file(photo)
                        .params(new LinkedMultiValueMap<>(
                                Map.of(
                                        "visible",List.of("true"),
                                        "title",List.of("gdfsgfd"),
                                        "description",List.of("gdfsgfd")
                                )
                        )))
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
    void createUnpublishedPostTest_Success() throws Exception {

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

        PostPostDto postPostDto = new PostPostDto();

        postPostDto.setTitle("gggg");
        postPostDto.setTags(new ArrayList<>());
        postPostDto.setVisible(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postPostDto))
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
    void deletePostTest_Success() throws Exception {
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

        Post post = Post.builder()
                .account(account)
                .title("HEHEHE")
                .tags(new ArrayList<>())
                .creationDate(LocalDate.now())
                .isVisible(true)
                .isPublished(true)
                .files(new ArrayList<>(List.of(postObjectRepository.findById(1L).orElse(null))))
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID,post.getId())
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
    void updatePostTest_Success() throws Exception {
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
                        .put( ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID , post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postPutDto))
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
    void updatePostPublishTest_Success() throws Exception {
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
        Post post = Post.builder()
                .account(account)
                .title("HEHEHE")
                .tags(new ArrayList<>())
                .creationDate(LocalDate.now())
                .isVisible(true)
                .isPublished(false)
                .files(List.of(postObjectRepository.findById(1L).orElse(null)))
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders
                        .post( ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID , post.getId())
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
    void getUnpublishedPostTest_Success() throws Exception {
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
        Post post = Post.builder()
                .account(account)
                .title("HEHEHE")
                .tags(new ArrayList<>())
                .creationDate(LocalDate.now())
                .isVisible(true)
                .isPublished(false)
                .files(List.of(postObjectRepository.findById(1L).orElse(null)))
                .build();

        postRepository.save(post);

        mockMvc.perform(MockMvcRequestBuilders
                        .get( ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH )
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
    void getAllPostsWithContentTest_Success() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setDefaultPagingAndSorting();

        mockMvc.perform(MockMvcRequestBuilders
                        .post( ApiPaths.Version.v2 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ALL, 1L)
                        .content(objectMapper.writeValueAsString(postRequestDto))
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
    void getAllPostsWithoutContentTest_Success() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setDefaultPagingAndSorting();

        mockMvc.perform(MockMvcRequestBuilders
                        .post( ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ALL, 1L)
                        .content(objectMapper.writeValueAsString(postRequestDto))
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
    void getPostWithContentTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ApiPaths.Version.v2 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID , 1L)
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
    void getPostWithoutContentTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.Post.POST_ID , 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
