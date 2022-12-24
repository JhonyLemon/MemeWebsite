package pl.jhonylemon.memewebsite.service.profilepicture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureInvalidParamException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.ProfilePhotoMapper;
import pl.jhonylemon.memewebsite.repository.ProfilePhotoRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfilePhotoService {

    private final ProfilePhotoRepository profilePhotoRepository;
    private final ProfilePhotoMapper profilePhotoMapper;

    @Transactional
    public ProfilePictureGetDto addProfilePicture(MultipartFile file) {
        if (file == null) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture profilePicture;
        try {
            profilePicture = ProfilePicture.builder()
                    .defaultProfile(false)
                    .mimeType(file.getContentType())
                    .file(file.getBytes())
                    .build();
        } catch (Exception e) {
            throw new ProfilePictureInvalidParamException();
        }
        profilePhotoRepository.save(profilePicture);
        return profilePhotoMapper.profilePictureToGetDto(profilePicture);
    }

    @Transactional
    public ProfilePictureGetDto changeDefaultProfilePicture(Long id) {
        if (id == null || id < 1) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture defaulProfile = profilePhotoRepository.findByDefaultProfileTrue().orElseThrow(() -> {
            throw new ProfilePictureNotFoundException();
        });
        defaulProfile.setDefaultProfile(false);
        ProfilePicture newDefaulProfile = profilePhotoRepository.findById(id).orElseThrow(() -> {
            throw new ProfilePictureNotFoundException();
        });
        newDefaulProfile.setDefaultProfile(true);
        return profilePhotoMapper.profilePictureToGetDto(newDefaulProfile);
    }

    public List<ProfilePictureGetDto> getAllProfilePictures() {
        return profilePhotoRepository.findAll().stream()
                .map(profilePhotoMapper::profilePictureToGetDto)
                .collect(Collectors.toList());
    }

    public ProfilePictureGetDto getProfilePicture(Long id) {
        if (id == null || id < 1) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture profilePicture = profilePhotoRepository.findById(id).orElseThrow(() -> {
            throw new ProfilePictureNotFoundException();
        });
        return profilePhotoMapper.profilePictureToGetDto(profilePicture);
    }

    @Transactional
    public void removeProfilePicture(Long id) {
        if (id == null || id < 1) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture profilePicture = profilePhotoRepository.findById(id).orElseThrow(() -> {
            throw new ProfilePictureNotFoundException();
        });
        if (profilePicture.getDefaultProfile()) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture defaultProfile = profilePhotoRepository.findByDefaultProfileTrue().orElseThrow(() -> {
            throw new ProfilePictureNotFoundException();
        });

        if (profilePicture.getAccounts() != null && !profilePicture.getAccounts().isEmpty()) {
            profilePicture.getAccounts().forEach(p -> p.setProfilePicture(defaultProfile));
            profilePicture.getAccounts().clear();
        }
        profilePhotoRepository.delete(profilePicture);
    }
}
