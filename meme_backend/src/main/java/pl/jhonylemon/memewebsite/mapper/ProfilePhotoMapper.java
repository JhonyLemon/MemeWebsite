package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;
import pl.jhonylemon.memewebsite.model.ProfilePictureGetModelApi;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfilePhotoMapper {

    ProfilePictureGetDto profilePictureToGetDto(ProfilePicture profilePicture);

    @Mapping(target = "content", source = "file")
    ProfilePictureGetModelApi profilePictureGetDtoToModelApi (ProfilePictureGetDto profilePictureGetDto);

}
