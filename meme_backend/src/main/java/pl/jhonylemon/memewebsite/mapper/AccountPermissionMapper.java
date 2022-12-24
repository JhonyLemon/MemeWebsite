package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.accountpermission.AccountPermissionGetDto;
import pl.jhonylemon.memewebsite.entity.AccountPermission;
import pl.jhonylemon.memewebsite.model.AccountPermissionGetModelApi;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountPermissionMapper {

    AccountPermissionGetDto accountPermissionToGetDto(AccountPermission accountPermission);

    AccountPermissionGetModelApi accountPermissionGetDtoToModelApi(AccountPermissionGetDto accountPermissionGetDto);

}
