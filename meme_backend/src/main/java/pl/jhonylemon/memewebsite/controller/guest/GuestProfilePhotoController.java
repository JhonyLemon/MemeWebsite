package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.mapper.ProfilePictureMapper;
import pl.jhonylemon.memewebsite.service.profilepicture.guest.GuestProfilePictureService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.ProfilePicture.PROFILE_PICTURE_PATH)
@CrossOrigin
public class GuestProfilePhotoController {

    private final ProfilePictureMapper profilePictureMapper;
    private final GuestProfilePictureService profilePictureService;

    @GetMapping(path = ApiPaths.ProfilePicture.PROFILE_PICTURE_GET)
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id){
        ProfilePictureGetDto profilePictureGetDto = profilePictureService.getProfilePicture(id);
        MediaType type = MediaType.parseMediaType(profilePictureGetDto.getMimeType());
        String contentDisposition = "attachment;";

        return ResponseEntity.ok()
                .contentType(type)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(profilePictureGetDto.getFile());
    }
    @GetMapping(path = ApiPaths.ProfilePicture.PROFILE_PICTURE_GET_ALL)
    public ResponseEntity<List<ProfilePictureGetDto>> getAllProfilePictures(){
        return ResponseEntity.ok().body(profilePictureService.getAllProfilePictures());
    }

}
