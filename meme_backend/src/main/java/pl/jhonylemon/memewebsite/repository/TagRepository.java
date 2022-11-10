package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}
