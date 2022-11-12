package pl.jhonylemon.memewebsite.service.profilepicture.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureInvalidParamException;
import pl.jhonylemon.memewebsite.exception.profilepicture.ProfilePictureNotFoundException;
import pl.jhonylemon.memewebsite.mapper.ProfilePictureMapper;
import pl.jhonylemon.memewebsite.repository.ProfilePictureRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestProfilePictureService {
    private final ProfilePictureRepository profilePictureRepository;
    private final ProfilePictureMapper profilePictureMapper;

    public ProfilePictureGetDto getProfilePicture(Long id){
        if (id == null || id<1) {
            throw new ProfilePictureInvalidParamException();
        }
        ProfilePicture profilePicture = profilePictureRepository.findById(id).orElseThrow(()->{
            throw new ProfilePictureNotFoundException();
        });
        return profilePictureMapper.profilePictureToGetDto(profilePicture);
    }
    public List<ProfilePictureGetDto> getAllProfilePictures(){
        return profilePictureRepository.findAll().stream()
                .map(profilePictureMapper::profilePictureToGetDto)
                .collect(Collectors.toList());
    }

}