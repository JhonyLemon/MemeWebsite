package pl.jhonylemon.memewebsite.service.profilepicture;

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
class ModeratorProfilePictureServiceTest {

    @Test
    void addProfilePictureTest_Success(){

    }

    @Test
    void changeDefaultProfilePictureTest_Success(){

    }

    @Test
    void removeProfilePictureTest_Success(){

    }


}