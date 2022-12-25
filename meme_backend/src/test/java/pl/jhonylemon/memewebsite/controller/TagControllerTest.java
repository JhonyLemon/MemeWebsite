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
import pl.jhonylemon.memewebsite.api.TagApi;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.tag.TagRequestDto;
import pl.jhonylemon.memewebsite.mapper.TagMapper;
import pl.jhonylemon.memewebsite.model.TagGetModelApi;
import pl.jhonylemon.memewebsite.model.TagPageGetModelApi;
import pl.jhonylemon.memewebsite.model.TagRequestModelApi;
import pl.jhonylemon.memewebsite.service.tag.TagService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class TagControllerTest {

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
    void addTagTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post( ApiPaths.Version.v1 + ApiPaths.Tag.TAG_PATH + ApiPaths.Tag.TAG_TAG , "Catto")
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
    void changeTagTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put(ApiPaths.Version.v1 + ApiPaths.Tag.TAG_PATH + ApiPaths.Tag.TAG_ID+ApiPaths.Tag.TAG_TAG, 2L,"test")
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
    void deleteTagTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(ApiPaths.Version.v1 + ApiPaths.Tag.TAG_PATH + ApiPaths.Tag.TAG_ID, 1L)
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
    void getAllTagsTest_Success() throws Exception {
        TagRequestDto tagRequestDto = new TagRequestDto();
        tagRequestDto.setDefaultPagingAndSorting();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApiPaths.Version.v1 + ApiPaths.Tag.TAG_PATH + ApiPaths.Tag.TAG_GET_ALL_PAGINATED)
                        .content(objectMapper.writeValueAsString(tagRequestDto))
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
    void getTagTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(ApiPaths.Version.v1 + ApiPaths.Tag.TAG_PATH + ApiPaths.Tag.TAG_ID,1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
