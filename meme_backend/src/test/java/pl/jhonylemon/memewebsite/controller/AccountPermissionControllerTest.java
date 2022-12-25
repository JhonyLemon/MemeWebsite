package pl.jhonylemon.memewebsite.controller;

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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class AccountPermissionControllerTest{


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
    void getPermissionTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(
                                ApiPaths.Version.v1 + ApiPaths.AccountPermission.ACCOUNT_PERMISSION_PATH + ApiPaths.AccountPermission.ACCOUNT_PERMISSION_ID,
                                1L
                        )
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
    void getPermissionsTest_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(
                                ApiPaths.Version.v1 + ApiPaths.AccountPermission.ACCOUNT_PERMISSION_PATH + ApiPaths.AccountPermission.ACCOUNT_PERMISSION_GET_ALL_PAGINATED
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
}
