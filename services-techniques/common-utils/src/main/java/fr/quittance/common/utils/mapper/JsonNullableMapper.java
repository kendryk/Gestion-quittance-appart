package fr.quittance.common.utils.mapper;

import fr.quittance.common.enums.CiviliteEnum;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

public class JsonNullableMapper {

    // Mapping générique : T -> JsonNullable<T>
    @Named("toJsonNullable")
    public static <T> JsonNullable<T> toJsonNullable(T value) {
        return value == null ? JsonNullable.undefined() : JsonNullable.of(value);
    }

    // Mapping générique : JsonNullable<T> -> T
    @Named("fromJsonNullable")
    public static <T> T fromJsonNullable(JsonNullable<T> value) {
        return value != null && value.isPresent() ? value.get() : null;
    }


    @Named("stringToJsonNullable")
    public static JsonNullable<String> stringToJsonNullable(String value) {
        return value == null ? JsonNullable.undefined() : JsonNullable.of(value);
    }

    @Named("jsonNullableToString")
    public static String jsonNullableToString(JsonNullable<String> value) {
        return (value != null && value.isPresent()) ? value.get() : null;
    }

    // Mapping spécifique : CiviliteEnum -> JsonNullable<CiviliteEnum>
    @Named("civiliteToJsonNullable")
    public static JsonNullable<CiviliteEnum> civiliteToJsonNullable(CiviliteEnum civilite) {
        return civilite == null ? JsonNullable.undefined() : JsonNullable.of(civilite);
    }

    // Mapping spécifique : JsonNullable<CiviliteEnum> -> CiviliteEnum
    @Named("jsonNullableToCivilite")
    public static CiviliteEnum jsonNullableToCivilite(JsonNullable<CiviliteEnum> jsonNullable) {
        return jsonNullable != null && jsonNullable.isPresent() ? jsonNullable.get() : null;
    }


}
