package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.AccountPermission;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountPermissionRepository extends JpaRepository<AccountPermission,Long> {
    @Query("select a from AccountPermission a where a.defaultPermission = true")
    List<AccountPermission> findByDefaultPermissionTrue();

    @Query("select a from AccountPermission a where a.permission like 'USER_READ'")
    Optional<AccountPermission> userReadPermission();

    @Query("select a from AccountPermission a where a.permission like 'USER_ADD'")
    Optional<AccountPermission> userWritePermission();
    @Query("select a from AccountPermission a where a.permission like 'USER_EDIT'")
    Optional<AccountPermission> userEditPermission();
    @Query("select a from AccountPermission a where a.permission like 'USER_DELETE'")
    Optional<AccountPermission> userDeletePermission();

    @Query("select a from AccountPermission a where a.permission like 'MODERATOR_ADD'")
    Optional<AccountPermission> moderatorAddPermission();

    @Query("select a from AccountPermission a where a.permission like 'MODERATOR_EDIT'")
    Optional<AccountPermission> moderatorEditPermission();
    @Query("select a from AccountPermission a where a.permission like 'MODERATOR_READ'")
    Optional<AccountPermission> moderatorReadPermission();

    @Query("select a from AccountPermission a where a.permission like 'MODERATOR_DELETE'")
    Optional<AccountPermission> moderatorDeletePermission();

    @Query("select a from AccountPermission a where a.permission like 'ADMIN_READ'")
    Optional<AccountPermission> adminReadPermission();

    @Query("select a from AccountPermission a where a.permission like 'ADMIN_EDIT'")
    Optional<AccountPermission> adminEditPermission();

    @Query("select a from AccountPermission a where a.permission like 'ADMIN_DELETE'")
    Optional<AccountPermission> adminDeletePermission();

    @Query("select a from AccountPermission a where a.permission like 'ADMIN_ADD'")
    Optional<AccountPermission> adminAddPermission();

}

