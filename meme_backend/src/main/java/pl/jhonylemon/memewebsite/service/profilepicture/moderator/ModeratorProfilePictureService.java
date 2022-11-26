package pl.jhonylemon.memewebsite.service.profilepicture.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureInvalidParamException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.ProfilePictureMapper;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ModeratorProfilePictureService {
    private final ProfilePictureRepository profilePictureRepository;
    private final ProfilePictureMapper profilePictureMapper;

    public ProfilePictureGetDto addProfilePicture(MultipartFile file){
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
        }catch (Exception e){
            throw new ProfilePictureInvalidParamException();
        }
        profilePictureRepository.save(profilePicture);
        return profilePictureMapper.profilePictureToGetDto(profilePicture);
    }

    @Transactional
    public ProfilePictureGetDto changeDefaultProfilePicture(Long id){
        if (id == null || id<1) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture defaulProfile = profilePictureRepository.findByDefaultProfileTrue().orElseThrow(()->{
            throw new ProfilePictureNotFoundException();
        });
        defaulProfile.setDefaultProfile(false);
        ProfilePicture newDefaulProfile = profilePictureRepository.findById(id).orElseThrow(()->{
            throw new ProfilePictureNotFoundException();
        });
        newDefaulProfile.setDefaultProfile(true);
        return profilePictureMapper.profilePictureToGetDto(newDefaulProfile);
    }
    @Transactional
    public void removeProfilePicture(Long id){
        if (id == null || id<1) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture profilePicture = profilePictureRepository.findById(id).orElseThrow(()->{
            throw new ProfilePictureNotFoundException();
        });
        if(profilePicture.getDefaultProfile()){
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture defaultProfile = profilePictureRepository.findByDefaultProfileTrue().orElseThrow(()->{
            throw new ProfilePictureNotFoundException();
        });

        if(profilePicture.getAccounts()!=null){
            if(!profilePicture.getAccounts().isEmpty()){
                profilePicture.getAccounts().forEach(p->p.setProfilePicture(defaultProfile));
                profilePicture.getAccounts().clear();
            }
        }
        profilePictureRepository.delete(profilePicture);
    }


}