package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.CommentStatistic;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentStatisticRepository extends JpaRepository<CommentStatistic,Long> {
    @Query("select c from CommentStatistic c where c.comment.id = ?1")
    List<CommentStatistic> findByComment_Id(Long id);

    @Query("select c from CommentStatistic c where c.comment.id = ?1 and c.account.id = ?2")
    Optional<CommentStatistic> findByComment_IdAndAccount_Id(Long commentId, Long accountId);

}
