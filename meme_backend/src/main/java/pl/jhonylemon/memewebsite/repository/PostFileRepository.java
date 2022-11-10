package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.PostFile;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile,Long> {
}
