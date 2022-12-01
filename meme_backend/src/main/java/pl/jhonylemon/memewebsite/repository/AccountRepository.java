package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>, JpaSpecificationExecutor<Account> {
    @Query("select (count(a) > 0) from Account a where a.email = ?1")
    boolean isEmailTaken(String email);

    @Query("select a from Account a where a.email = ?1")
    Optional<Account> findByEmail(String email);

    @Query("select (count(a) > 0) from Account a where a.accountRole.role = 'ADMIN' and a.id = ?1")
    boolean isAccountAdmin(Long id);

    @Query("select (count(a) > 0) from Account a where a.accountRole.role = 'MODERATOR' and a.id = ?1")
    boolean isAccountModerator(Long id);

    @Query("select (count(a) > 0) from Account a where a.accountRole.role = 'USER' and a.id = ?1")
    boolean isAccountUser(Long id);



}
