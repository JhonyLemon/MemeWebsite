package pl.jhonylemon.memewebsite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.api.ProfilePhotoApi;
import pl.jhonylemon.memewebsite.mapper.ProfilePhotoMapper;
import pl.jhonylemon.memewebsite.model.ProfilePictureGetModelApi;
import pl.jhonylemon.memewebsite.service.profilepicture.ProfilePhotoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ProfilePhotoController implements ProfilePhotoApi {

    private final ProfilePhotoService profilePhotoService;
    private final ProfilePhotoMapper profilePhotoMapper;

    @Override
    public ResponseEntity<ProfilePictureGetModelApi> addProfilePicture(MultipartFile file) {
        return ResponseEntity.ok().body(
                profilePhotoMapper.profilePictureGetDtoToModelApi(
                        profilePhotoService.addProfilePicture(file)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_EDIT')")
    public ResponseEntity<ProfilePictureGetModelApi> changeDefaultProfilePicture(Long id) {
        return ResponseEntity.ok().body(
                profilePhotoMapper.profilePictureGetDtoToModelApi(
                        profilePhotoService.changeDefaultProfilePicture(id)
                )
        );
    }

    @Override
    public ResponseEntity<List<ProfilePictureGetModelApi>> getAllProfilePictures() {
        return ResponseEntity.ok().body(
                profilePhotoService.getAllProfilePictures().stream()
                        .map(profilePhotoMapper::profilePictureGetDtoToModelApi)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<ProfilePictureGetModelApi> getProfilePicture(Long id) {
        return ResponseEntity.ok().body(
                profilePhotoMapper.profilePictureGetDtoToModelApi(
                        profilePhotoService.getProfilePicture(id)
                )
        );
    }

    @Override
    @PreAuthorize("hasAuthority('MODERATOR_DELETE')")
    public ResponseEntity<Void> removeProfilePicture(Long id) {
        profilePhotoService.removeProfilePicture(id);
        return ResponseEntity.ok().build();
    }
}
