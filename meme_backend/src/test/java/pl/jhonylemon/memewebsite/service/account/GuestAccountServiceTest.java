package pl.jhonylemon.memewebsite.service.account;

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
class GuestAccountServiceTest {

    @Test
    void getAllAccountsTest_Success() {

    }

    @Test
    void createAccountTest_Success() {

    }

    @Test
    void getAccountShortTest_Success() {

    }

    @Test
    void getAccountFullTest_Success() {

    }

}
