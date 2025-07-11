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


    @Test
    public void testStaticMethodAccessible() {
        Timestamp timestamp = Timestamp.valueOf("2023-01-01 00:00:00");

        // Appel de la méthode
        LocalDate result = DateMapperUtil.timestampToLocalDate(timestamp);

        assertNotNull(result);
        assertEquals(LocalDate.of(2023, 1, 1), result);

    }

}
