package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.AccountRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole,Long> {

    @Query("select a from AccountRole a where a.isDefaultRole = true")
    Optional<AccountRole> findByDefaultRoleTrue();

    @Query("select a.accounts from AccountRole a where a.role = 'ADMIN'")
    List<Account> findAdmins();

    @Query("select a.accounts from AccountRole a where a.role = 'MODERATOR'")
    List<Account> findModerators();

    @Query("select a.accounts from AccountRole a where a.role = 'USER'")
    List<Account> findUsers();





}
