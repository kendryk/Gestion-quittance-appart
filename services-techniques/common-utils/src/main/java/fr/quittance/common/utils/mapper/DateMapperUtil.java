package fr.quittance.common.utils.mapper;

import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DateMapperUtil {

    private DateMapperUtil() {
    }

    // Conversion de LocalDate en Timestamp
    @Named("localDateToTimestamp")
    public static Timestamp localDateToTimestamp(LocalDate localDate) {
        return localDate != null ? Timestamp.valueOf(localDate.atStartOfDay()) : null;
    }

    // Conversion de Timestamp en LocalDate
    @Named("timestampToLocalDate")
    public static LocalDate  timestampToLocalDate(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    // Conversion de Date en LocalDate
    @Named("dateToLocalDate")
    public static LocalDate dateToLocalDate(Date date) {
        return date != null ? Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    // Conversion de LocalDate en Date
    @Named("localDateToDate")
    public static Date localDateToDate(LocalDate localDate) {
        return localDate != null ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
    }

    // Conversion de LocalDateTime en String
    @Named("localDateTimeToString")
    public static String localDateTimeToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }

    @Named("localDateToString")
    public static String localDateToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    // Conversion de String en LocalDateTime
    @Named("stringToLocalDateTime")
    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        if (dateTime != null) {
            // Si vous avez une date sans heure, vous pouvez utiliser le format 'yyyy-MM-dd'
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(dateTime, formatter).atStartOfDay();  // Ajoute minuit comme heure par défaut
        }
        return null;
    }

    @Named("stringToTimestamp")
    public static Timestamp stringToTimestamp(String dateString) {
        if (dateString == null || dateString.trim().isBlank()) {
            return null;
        }
        OffsetDateTime odt = OffsetDateTime.parse(dateString);
        return Timestamp.valueOf(odt.toLocalDateTime());
    }

    @Named("timestampToString")
    public static String timestampToString(Timestamp timestamp) {
        return (timestamp == null) ? null : timestamp.toString();
    }

}
