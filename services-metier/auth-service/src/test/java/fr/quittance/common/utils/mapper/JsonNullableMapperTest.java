package fr.quittance.common.utils.mapper;

import fr.quittance.common.enums.CiviliteEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonNullableMapperTest {


    @Test
    void testStringToJsonNullable() {
        JsonNullable<String> result = JsonNullableMapper.stringToJsonNullable("test");
        assertNotNull(result);
        assertEquals("test", result.get());
    }

    @Test
    void testJsonNullableToString() {
        JsonNullable<String> result = JsonNullable.of("value");
        assertEquals("value", JsonNullableMapper.jsonNullableToString(result));
    }

    @Test
    void testCiviliteEnumToJsonNullable() {
        JsonNullable<CiviliteEnum> result = JsonNullableMapper.civiliteToJsonNullable(CiviliteEnum.M);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(CiviliteEnum.M, result.get());
    }

    @Test
    void testJsonNullableToCiviliteEnum() {
        JsonNullable<CiviliteEnum> input = JsonNullable.of(CiviliteEnum.MME);
        CiviliteEnum result = JsonNullableMapper.jsonNullableToCivilite(input);
        assertNotNull(result);
        assertEquals(CiviliteEnum.MME, result);
    }
}
