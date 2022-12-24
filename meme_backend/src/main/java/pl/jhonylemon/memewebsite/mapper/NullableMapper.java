package pl.jhonylemon.memewebsite.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NullableMapper {
    default <T> T mapNullableToValue(JsonNullable<T> nullable) {
        return nullable.get();
    }

    default <T> JsonNullable<T> mapValueToNullable(T value) {
        return JsonNullable.of(value);
    }

    @Condition
    default <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent() && nullable.get() != null && !nullable.get().equals("");
    }
}
