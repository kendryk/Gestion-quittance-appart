package fr.quittance.common.utils.mapper;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DateMapperUtilTest {

    /**
     * Test de la méthode {@link DateMapperUtil#localDateToTimestamp(LocalDate)}
     * Vérifie que la méthode retourne un objet Timestamp valide lorsque l'entrée est non nulle.
     */
    @Test
    void testLocalDateToTimestamp_WithValidLocalDate_ReturnsTimestamp() {
        // Initialisation d'une date LocalDate
        LocalDate localDate = LocalDate.of(2023, 10, 20);

        // Appel de la méthode à tester
        Timestamp result = DateMapperUtil.localDateToTimestamp(localDate);

        // Vérification que le résultat est égal à la date correspondante en Timestamp
        assertNotNull(result);
        assertEquals("2023-10-20 00:00:00.0", result.toString());
    }

    /**
     * Test de la méthode {@link DateMapperUtil#localDateToTimestamp(LocalDate)}
     * Vérifie que la méthode retourne null lorsque l'entrée est null.
     */
    @Test
    void testLocalDateToTimestamp_WithNullLocalDate_ReturnsNull() {
        // Appel de la méthode avec une entrée null
        Timestamp result = DateMapperUtil.localDateToTimestamp(null);

        // Vérification que le résultat est null
        assertNull(result);
    }
}
