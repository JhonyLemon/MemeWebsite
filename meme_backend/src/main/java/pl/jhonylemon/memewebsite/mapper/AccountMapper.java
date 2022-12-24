package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.jhonylemon.memewebsite.dto.account.*;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationRefreshRequest;
import pl.jhonylemon.memewebsite.dto.authentication.AuthenticationResponse;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.model.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                AccountRoleMapper.class,
                ProfilePhotoMapper.class,
                MiscMapper.class,
                NullableMapper.class
        }
)
public interface AccountMapper {


    @Mapping(source = "profilePicture.id", target = "profilePhotoId")
    @Mapping(source = "accountRole", target = "role")
    AccountGetFullDto accountToFullGetDto(Account account);

    @Mapping(source = "profilePicture.id", target = "profilePhotoId")
    AccountGetShortDto accountToShortGetDto(Account account);

    AccountGetFullModelApi accountFullDtoToModelApi(AccountGetFullDto accountGetFullDto);

    AccountGetShortModelApi accountShortDtoToModelApi(AccountGetShortDto accountGetShortDto);

    AuthenticationResponseModelApi authDtoToModelApi(AuthenticationResponse authenticationResponse);

    AccountPostDto accountPostModelApiToDto(AccountPostModelApi accountPostModelApi);

    AccountPageGetModelApi accountPageDtoToModelApi(AccountPageGetDto accountPageGetDto);

    AccountPutPasswordDto accountPutPasswordUserModelApiToDto(AccountPutPasswordUserModelApi accountPutPasswordUserModelApi);

    AccountPutPasswordDto accountPutPasswordAdminModelApiToDto(AccountPutPasswordAdminModelApi accountPutPasswordAdminModelApi);

    AuthenticationRefreshRequest accountAuthModelApiToDto(AuthenticationRefreshRequestModelApi authenticationRefreshRequestModelApi);

    AccountRequestDto accountRequestModelApiToDto(AccountRequestModelApi accountRequestModelApi);

    AccountPagingAndSortingRequestDto accountPagingAndSortingModelApiToDto(AccountPagingAndSortingRequestModelApi accountPagingAndSortingRequestModelApi);

    AccountFilterDto accountFilterModelApiToDto(AccountFilterModelApi accountFilterModelApi);


}
