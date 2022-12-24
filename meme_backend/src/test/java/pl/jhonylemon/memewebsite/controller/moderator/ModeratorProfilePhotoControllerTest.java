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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.repository.ProfilePhotoRepository;

import java.io.FileInputStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class ModeratorProfilePhotoControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Autowired
    ProfilePhotoRepository profilePhotoRepository;

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
    void addProfilePictureTest_Success() throws Exception {

        MockMultipartFile photo = new MockMultipartFile(
                "file",
                "profile.png",
                MediaType.IMAGE_PNG_VALUE,
                new FileInputStream(
                        ResourceUtils
                                .getFile("classpath:photo/profile.png")
                ).readAllBytes()
                );

        mockMvc.perform(MockMvcRequestBuilders.multipart(ApiPaths.Moderator.MODERATOR_PATH + ApiPaths.ProfilePicture.PROFILE_PICTURE_PATH +
                                ApiPaths.ProfilePicture.PROFILE_PICTURE_ADD)
                        .file(photo))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {
            "MODERATOR_ADD",
            "MODERATOR_READ",
            "MODERATOR_EDIT",
            "MODERATOR_DELETE"
    })
    @Test
    void changeDefaultProfilePictureTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put(ApiPaths.Moderator.MODERATOR_PATH + ApiPaths.ProfilePicture.PROFILE_PICTURE_PATH +
                                ApiPaths.ProfilePicture.PROFILE_PICTURE_CHANGE, 1L)
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
    void removeProfilePictureTest_Success() throws Exception {


        ProfilePicture profilePicture = ProfilePicture.builder()
                .file(
                        new FileInputStream(
                                ResourceUtils
                                        .getFile("classpath:photo/profile.png")
                        ).readAllBytes()
                )
                .mimeType("image/png")
                .defaultProfile(false)
                .build();

        profilePhotoRepository.save(profilePicture);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ApiPaths.Moderator.MODERATOR_PATH + ApiPaths.ProfilePicture.PROFILE_PICTURE_PATH +
                                ApiPaths.ProfilePicture.PROFILE_PICTURE_DELETE, profilePicture.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

}
