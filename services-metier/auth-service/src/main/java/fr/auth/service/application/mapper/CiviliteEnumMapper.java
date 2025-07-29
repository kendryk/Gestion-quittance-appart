package fr.auth.service.application.mapper;

import fr.auth.service.enums.CiviliteEnum;
import fr.authservice.domain.model.UtilisateurUpdateDto;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

public class CiviliteEnumMapper {

    @Named("civiliteToJsonNullable")
    public JsonNullable<UtilisateurUpdateDto.CiviliteEnum> civiliteToJsonNullable(CiviliteEnum civilite) {
        if (civilite == null) {
            return JsonNullable.undefined();
        }
        return JsonNullable.of(UtilisateurUpdateDto.CiviliteEnum.valueOf(civilite.name()));
    }

    @Named("jsonNullableToCivilite")
    public CiviliteEnum jsonNullableToCivilite(JsonNullable<UtilisateurUpdateDto.CiviliteEnum> jsonNullable) {
        if (jsonNullable == null || !jsonNullable.isPresent()) {
            return null;
        }
        return CiviliteEnum.valueOf(jsonNullable.get().name());
    }
}
