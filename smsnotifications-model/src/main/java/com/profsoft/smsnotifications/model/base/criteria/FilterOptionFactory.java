package com.profsoft.smsnotifications.model.base.criteria;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author Maroo
 */
public class FilterOptionFactory {

    private static Logger log = Logger.getLogger(FilterOptionFactory.class.getName());

    public static FilterOption create(String field, Object value) {
        FilterOperator type = FilterOperator.EQUALS;
        String fieldName = field;

        String[] fieldArray = field.split("_");
        if (fieldArray.length > 1) {
            type = FilterOperator.getByCode(fieldArray[0]);
            fieldName = fieldArray[fieldArray.length - 1];
        }
        Object castedValue = createValue(type, value);
        return new FilterOption(fieldName, castedValue, type);

    }

    private static Object createValue(FilterOperator type, Object value) {
        switch (type) {
            case EQUALS_DATE:
                return createDateTimeStartDay(value);
            case LESSER:
                return createDateTime(value);
            case GREATER:
                return createDateTime(value);
            case GREATER_EQUALS:
                return createDateTime(value);
            case LESSER_EQUALS:
                return createDateTime(value);
            default:
                return value.toString();
        }
    }

    private static LocalDateTime createDateTime(Object value) {
        String format = "yyyy-MM-dd HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDateTime.parse(value.toString(), formatter);
        } catch (DateTimeParseException ex) {
            log.error("Błąd formatowania daty i czau. Oczekiwany format to " + format, ex);
        }
        return null;
    }

    private static LocalDateTime createDateTimeStartDay(Object value) {
        String format = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            return LocalDate.parse(value.toString(), formatter).atStartOfDay();
        } catch (DateTimeParseException ex) {
            log.error("Błąd formatowania daty. Oczekiwany format to " + format, ex);
        }
        return null;
    }
}
