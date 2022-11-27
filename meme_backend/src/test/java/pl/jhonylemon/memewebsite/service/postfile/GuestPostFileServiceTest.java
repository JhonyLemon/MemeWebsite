package pl.jhonylemon.memewebsite.service.postfile;

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
class GuestPostFileServiceTest {

    @Test
    void getFullPostFileByIdTest_Success(){

    }

    @Test
    void getShortPostFileByIdTest_Success(){

    }

    @Test
    void getPostFileFileByIdTest_Success(){

    }

}
