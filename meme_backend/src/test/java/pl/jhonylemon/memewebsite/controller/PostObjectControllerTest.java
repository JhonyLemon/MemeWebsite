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
import org.springframework.http.HttpMethod;
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
import pl.jhonylemon.memewebsite.api.PostObjectApi;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.mapper.PostObjectMapper;
import pl.jhonylemon.memewebsite.model.PostObjectFullGetModelApi;
import pl.jhonylemon.memewebsite.service.postobject.PostObjectService;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class PostObjectControllerTest {

    @Autowired
    MockMvc mockMvc;

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
    void createPostObjectTest_Success() throws Exception {
        MockMultipartFile photo = new MockMultipartFile(
                "file",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(
                        ResourceUtils
                                .getFile("classpath:photo/profile.png")
                ).readAllBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(ApiPaths.Version.v1 + ApiPaths.Post.POST_PATH + ApiPaths.PostObject.POST_OBJECT_ID + ApiPaths.PostObject.POST_OBJECT_PATH + ApiPaths.PostObject.POST_OBJECT_ORDER,1L,1L)
                        .file(photo)
                        .params(new LinkedMultiValueMap<>(
                                Map.of(
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
    void deletePostObjectTest_Success() throws Exception {

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
    void getPostObjectTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ApiPaths.Version.v1 + ApiPaths.PostObject.POST_OBJECT_PATH + ApiPaths.PostObject.POST_OBJECT_ID, 1L)
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
    void updatePostObjectTest_Success() throws Exception {

        MockMultipartFile photo = new MockMultipartFile(
                "file",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(
                        ResourceUtils
                                .getFile("classpath:photo/profile.png")
                ).readAllBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT,ApiPaths.Version.v1 + ApiPaths.PostObject.POST_OBJECT_PATH + ApiPaths.PostObject.POST_OBJECT_ID,1L)
                        .file(photo)
                        .params(new LinkedMultiValueMap<>(
                                Map.of(
                                        "description",List.of("gdfsgfd")
                                )
                        )))
                .andExpect(status().isOk());
    }
}
