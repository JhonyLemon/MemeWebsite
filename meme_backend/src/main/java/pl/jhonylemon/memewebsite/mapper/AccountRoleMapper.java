package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.accountrole.AccountRoleGetDto;
import pl.jhonylemon.memewebsite.entity.AccountRole;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                AccountPermissionMapper.class
        }
)
public interface AccountRoleMapper {

    AccountRoleGetDto accountRoleToGetDto(AccountRole accountRole);

}
