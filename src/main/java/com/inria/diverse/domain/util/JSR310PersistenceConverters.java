package com.inria.diverse.domain.util;

import com.inria.diverse.domain.util.JSR310DateConverters.*;

import java.time.*;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public final class JSR310PersistenceConverters {

    private JSR310PersistenceConverters() {}

    @Converter(autoApply = true)
    public static class LocalDateConverter implements AttributeConverter<LocalDate, java.sql.Date> {

         
        public java.sql.Date convertToDatabaseColumn(LocalDate date) {
            return date == null ? null : java.sql.Date.valueOf(date);
        }

         
        public LocalDate convertToEntityAttribute(java.sql.Date date) {
            return date == null ? null : date.toLocalDate();
        }
    }

    @Converter(autoApply = true)
    public static class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Date> {

         
        public Date convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
            return ZonedDateTimeToDateConverter.INSTANCE.convert(zonedDateTime);
        }

         
        public ZonedDateTime convertToEntityAttribute(Date date) {
            return DateToZonedDateTimeConverter.INSTANCE.convert(date);
        }
    }

    @Converter(autoApply = true)
    public static class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

         
        public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
            return LocalDateTimeToDateConverter.INSTANCE.convert(localDateTime);
        }

         
        public LocalDateTime convertToEntityAttribute(Date date) {
            return DateToLocalDateTimeConverter.INSTANCE.convert(date);
        }
    }
}
