package pl.jhonylemon.memewebsite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;

import java.util.Optional;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture,Long> {

    @Query("select p from ProfilePicture p where p.defaultProfile = true")
    Optional<ProfilePicture> findByDefaultProfileTrue();

}
