package pl.jhonylemon.memewebsite.service.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.account.AccountPutPasswordDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.repository.AccountPermissionRepository;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.AccountRoleRepository;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;
import pl.jhonylemon.memewebsite.service.account.admin.AdminAccountService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@Transactional
class AdminAccountServiceTest {

    @MockBean
    AccountRepository accountRepository;

    @Autowired
    AdminAccountService accountService;

    @Autowired
    ProfilePictureRepository profilePictureRepository;

    @Autowired
    AccountPermissionRepository accountPermissionRepository;
    @Autowired
    public AccountRoleRepository accountRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    void updateAccountPasswordTest_Success() {

        Account account = Account.builder()
                .profilePicture(profilePictureRepository.findByDefaultProfileTrue().orElse(null))
                .password(encoder.encode("123456789"))
                .name("Gacek")
                .email("Gacek@gmail.com")
                .enabled(true)
                .banned(false)
                .creationDate(LocalDate.now())
                .id(1L)
                .accountRole(accountRoleRepository.findByDefaultRoleTrue().orElse(null))
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.findByEmail("Gacek@gmail.com")).thenReturn(Optional.of(account));

        AccountPutPasswordDto accountPutPasswordDto = new AccountPutPasswordDto();
        accountPutPasswordDto.setNewPassword("987654321");

        AccountGetFullDto accountGetFullDto = accountService.updateAccountPassword(1L,accountPutPasswordDto);

        assertNotNull(accountGetFullDto);
        assertTrue(encoder.matches("987654321",account.getPassword()));
    }

    @Test
    void deleteAccountTest_Success() {

    }

    @Test
    void getAccountShortTest_Success() {

    }

    @Test
    void getAccountFullTest_Success() {

    }

    @Test
    void getAccountPermissionTest_Success() {

    }

    @Test
    void changeAccountPermissionTest_Success() {

    }

    @Test
    void addAccountPermissionTest_Success() {

    }

    @Test
    void deleteAccountPermissionTest_Success() {

    }

}
