package pl.jhonylemon.memewebsite.service.poststatistic;

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
class UserPostStatisticServiceTest {

  @Test
   void setSeenStatisticTest_Success(){

    }

    @Test
    void setFavoriteStatisticTest_Success(){

    }

    @Test
    void setVoteStatisticTest_Success(){

    }

}
