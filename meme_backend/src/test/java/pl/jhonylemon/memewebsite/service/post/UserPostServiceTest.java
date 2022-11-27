package pl.jhonylemon.memewebsite.service.post;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class UserPostServiceTest {

    @Test
    void createPostTest_Success() {

    }

    @Test
    void updatePostSelfTest_Success(){

    }

    @Test
    void deletePostSelfTest_Success(){

    }

}
