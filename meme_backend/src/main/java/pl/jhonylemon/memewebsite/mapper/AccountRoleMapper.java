package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRolePostDto;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRolePutDto;
import pl.jhonylemon.memewebsite.entity.AccountRole;
import pl.jhonylemon.memewebsite.model.AccountRoleGetModelApi;
import pl.jhonylemon.memewebsite.model.AccountRolePostModelApi;
import pl.jhonylemon.memewebsite.model.AccountRolePutModelApi;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                AccountPermissionMapper.class
        }
)
public interface AccountRoleMapper {

    AccountRoleGetDto accountRoleToGetDto(AccountRole accountRole);
    AccountRoleGetModelApi accountRoleGetDtoToModelApi(AccountRoleGetDto accountRoleGetDto);
    AccountRolePutDto accountRolePutModelApiToDto(AccountRolePutModelApi accountRolePutModelApi);
    AccountRolePostDto accountRolePostModelApiToDto(AccountRolePostModelApi accountRolePostModelApi);

}
