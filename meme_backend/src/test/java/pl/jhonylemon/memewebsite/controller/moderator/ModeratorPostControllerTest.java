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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.post.PostPutDto;

import java.util.ArrayList;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class ModeratorPostControllerTest {

    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper;

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
    void deletePostTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete( ApiPaths.Post.POST_PATH , 1L)
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
    void updatePostTest_Success() throws Exception {

        PostPutDto postPutDto = new PostPutDto();
        postPutDto.setTitle("hfhfh");
        postPutDto.setOrder(Map.of(
                1L,1L,
                2L,2L
        ));
        postPutDto.setVisible(true);
        postPutDto.setTags(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders
                        .put(ApiPaths.Post.POST_PATH , 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postPutDto))
                )
                .andExpect(status().isOk());
    }

}
