package pl.jhonylemon.memewebsite.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.mapper.ProfilePictureMapper;
import pl.jhonylemon.memewebsite.service.profilepicture.moderator.ModeratorProfilePictureService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Moderator.MODERATOR_PATH+ApiPaths.ProfilePicture.PROFILE_PICTURE_PATH)
@CrossOrigin
public class ModeratorProfilePhotoController {

    private final ModeratorProfilePictureService profilePictureService;
    private final ProfilePictureMapper profilePictureMapper;

    @PostMapping(path = ApiPaths.ProfilePicture.PROFILE_PICTURE_ADD)
    public ResponseEntity<ProfilePictureGetDto> addProfilePicture(@RequestPart MultipartFile file){
        return ResponseEntity.ok().body(profilePictureService.addProfilePicture(file));
    }
    @PutMapping(path = ApiPaths.ProfilePicture.PROFILE_PICTURE_CHANGE)
    public ResponseEntity<ProfilePictureGetDto> changeDefaultProfilePicture(@PathVariable Long id){
        return ResponseEntity.ok().body(profilePictureService.changeDefaultProfilePicture(id));
    }
    @DeleteMapping(path = ApiPaths.ProfilePicture.PROFILE_PICTURE_DELETE)
    public ResponseEntity<Void> removeProfilePicture(@PathVariable Long id){
        profilePictureService.removeProfilePicture(id);
        return ResponseEntity.ok().build();
    }

}
