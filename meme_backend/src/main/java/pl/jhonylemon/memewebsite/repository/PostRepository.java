package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.Post;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {
    @Query("select count(p) from Post p where p.isPublished = false")
    Long findUnPublishedPostsCount();

    @Query("select p from Post p where p.isPublished = false")
    Optional<Post> findUnPublishedPost();

}
