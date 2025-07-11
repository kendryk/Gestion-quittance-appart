package fr.auth.service.application.mapper;

import fr.quittance.common.enums.CiviliteEnum;
import fr.authservice.domain.model.UtilisateurUpdateDto;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

@Component
public class CiviliteEnumMapper {

    // Conversion : CiviliteEnum -> JsonNullable<UtilisateurUpdateDto.CiviliteEnum>
    @Named("civiliteEnumToJsonNullableDtoEnum")
    public JsonNullable<UtilisateurUpdateDto.CiviliteEnum> civiliteEnumToJsonNullableDtoEnum(CiviliteEnum civilite) {
        if (civilite == null) {
            return JsonNullable.undefined();
        }

        UtilisateurUpdateDto.CiviliteEnum dtoCivilite = switch (civilite) {
            case M -> UtilisateurUpdateDto.CiviliteEnum.M;
            case MME -> UtilisateurUpdateDto.CiviliteEnum.MME;
        };

        return JsonNullable.of(dtoCivilite);
    }

    // Conversion : JsonNullable<UtilisateurUpdateDto.CiviliteEnum> -> CiviliteEnum
    @Named("jsonNullableDtoEnumToCiviliteEnum")
    public CiviliteEnum jsonNullableDtoEnumToCiviliteEnum(JsonNullable<UtilisateurUpdateDto.CiviliteEnum> jsonNullable) {
        if (jsonNullable == null || !jsonNullable.isPresent()) {
            return null;
        }

        UtilisateurUpdateDto.CiviliteEnum dtoCivilite = jsonNullable.get();
        return switch (dtoCivilite) {
            case M -> CiviliteEnum.M;
            case MME -> CiviliteEnum.MME;
        };
    }

    // Conversion directe : CiviliteEnum -> UtilisateurUpdateDto.CiviliteEnum
    @Named("civiliteEnumToDtoEnum")
    public UtilisateurUpdateDto.CiviliteEnum civiliteEnumToDtoEnum(CiviliteEnum civilite) {
        if (civilite == null) {
            return null;
        }

        return switch (civilite) {
            case M -> UtilisateurUpdateDto.CiviliteEnum.M;
            case MME -> UtilisateurUpdateDto.CiviliteEnum.MME;
        };
    }

    // Conversion directe : UtilisateurUpdateDto.CiviliteEnum -> CiviliteEnum
    @Named("dtoEnumToCiviliteEnum")
    public CiviliteEnum dtoEnumToCiviliteEnum(UtilisateurUpdateDto.CiviliteEnum dtoCivilite) {
        if (dtoCivilite == null) {
            return null;
        }

        return switch (dtoCivilite) {
            case M -> CiviliteEnum.M;
            case MME -> CiviliteEnum.MME;
        };
    }
}
