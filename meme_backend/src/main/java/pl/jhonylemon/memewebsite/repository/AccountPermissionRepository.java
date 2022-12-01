package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.AccountPermission;

@Repository
public interface AccountPermissionRepository extends JpaRepository<AccountPermission,Long> {

}

