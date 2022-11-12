package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.mapper.ProfilePictureMapper;
import pl.jhonylemon.memewebsite.service.profilepicture.guest.GuestProfilePictureService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.ProfilePicture.PROFILE_PICTURE_PATH)
public class GuestProfilePhotoController {

    private final ProfilePictureMapper profilePictureMapper;
    private final GuestProfilePictureService profilePictureService;

    @GetMapping(path = ApiPaths.ProfilePicture.PROFILE_PICTURE_GET)
    public ResponseEntity<ProfilePictureGetDto> getProfilePicture(@PathVariable Long id){
        return ResponseEntity.ok().body(profilePictureService.getProfilePicture(id));
    }
    @GetMapping(path = ApiPaths.ProfilePicture.PROFILE_PICTURE_GET_ALL)
    public ResponseEntity<List<ProfilePictureGetDto>> getAllProfilePictures(){
        return ResponseEntity.ok().body(profilePictureService.getAllProfilePictures());
    }

}
