package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.PostObject;

import java.util.List;

@Repository
public interface PostObjectRepository extends JpaRepository<PostObject,Long> {
    @Query("select p.id from PostObject p where p.post.id = ?1 and p.charset is null order by p.order")
    List<Long> findFirstByPostId(Long id, Pageable pageable);

    @Query("select p.id from PostObject p where p.post.id = ?1 order by p.order")
    List<Long> findPostObjectsByPostId(Long id);
}
