package org.acme.common.validation.validator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class DateWithRangeValidator implements Validator<String> {
  private final DateTimeFormatter formatter;
  private final Duration minDuration;
  private final Duration maxDuration;
  private final String errorMessage;

  public DateWithRangeValidator(String dateFormat, Duration minDuration, Duration maxDuration,
      String errorMessage) {
    if (minDuration.compareTo(maxDuration) > 0) {
      throw new IllegalArgumentException(
          "La duración mínima no puede ser mayor que la duración máxima.");
    }
    this.formatter = DateTimeFormatter.ofPattern(dateFormat);
    this.minDuration = minDuration;
    this.maxDuration = maxDuration;
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String dateStr) {
    if (dateStr == null || dateStr.isEmpty()) {
      return new ValidationResult(errorMessage);
    }

    try {
      LocalDate date = LocalDate.parse(dateStr, formatter);
      LocalDate now = LocalDate.now();
      LocalDate minDate = now.plus(minDuration);
      LocalDate maxDate = now.plus(maxDuration);

      if (date.isBefore(minDate) || date.isAfter(maxDate)) {
        return new ValidationResult(errorMessage);
      }
      return new ValidationResult();
    } catch (DateTimeParseException e) {
      return new ValidationResult(errorMessage);
    }
  }
}
