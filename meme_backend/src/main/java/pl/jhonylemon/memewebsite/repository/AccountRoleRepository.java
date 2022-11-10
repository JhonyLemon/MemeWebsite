package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.AccountRole;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole,Long> {
}
