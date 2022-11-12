package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.profilepicture.ProfilePictureGetDto;
import pl.jhonylemon.memewebsite.entity.ProfilePicture;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfilePictureMapper {

    ProfilePictureGetDto profilePictureToGetDto(ProfilePicture profilePicture);

}
