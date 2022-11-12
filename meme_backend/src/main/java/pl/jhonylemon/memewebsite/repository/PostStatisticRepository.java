package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.PostStatistic;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostStatisticRepository extends JpaRepository<PostStatistic,Long> {
    @Query("select p from PostStatistic p where p.post.id = ?1")
    List<PostStatistic> findByPost_Id(Long id);

    @Query("select p from PostStatistic p where p.post.id = ?1 and p.account.id = ?2")
    Optional<PostStatistic> findByPost_IdAndAccount_Id(Long postId, Long accountId);



}
