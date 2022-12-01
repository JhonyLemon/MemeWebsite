package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.account.AccountGetFullDto;
import pl.jhonylemon.memewebsite.dto.account.AccountGetShortDto;
import pl.jhonylemon.memewebsite.entity.Account;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
        AccountRoleMapper.class,
        ProfilePictureMapper.class
        }
)
public interface AccountMapper {


    @Mapping(source = "profilePicture.id", target = "profilePhotoId")
    AccountGetFullDto accountToFullGetDto(Account account);

    @Mapping(source = "profilePicture.id", target = "profilePhotoId")
    AccountGetShortDto accountToShortGetDto(Account account);

}
