package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.PostStatistic;

@Repository
public interface PostStatisticRepository extends JpaRepository<PostStatistic,Long> {
}
