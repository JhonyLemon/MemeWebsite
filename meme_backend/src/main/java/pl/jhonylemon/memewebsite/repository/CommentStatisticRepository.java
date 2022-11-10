package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.CommentStatistic;

@Repository
public interface CommentStatisticRepository extends JpaRepository<CommentStatistic,Long> {
}
