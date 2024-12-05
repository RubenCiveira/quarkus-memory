package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class DateWithRangeValidatorUnitTest {
  private final DateWithRangeValidator validator = new DateWithRangeValidator("dd/MM/yyyy",
      Duration.ofDays(30), Duration.ofDays(90), "Fecha inválida");

  @Test
  void testValidDateWithinRange() {
    DateTimeFormatter ftf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate now = LocalDate.now();
    assertTrue(validator.validate(now.plusDays(35).format(ftf)).isValid()); // Fecha dentro de 30 a
                                                                            // 90 días
    assertFalse(validator.validate(now.plusDays(5).format(ftf)).isValid()); // Antes de 30 días
    assertFalse(validator.validate(now.plusDays(100).format(ftf)).isValid()); // Después de 90 días

    assertFalse(validator.validate("2024-12-15").isValid()); // Formato no válido
    assertFalse(validator.validate(null).isValid());
    assertFalse(validator.validate("").isValid());

    Duration of30 = Duration.ofDays(30);
    Duration of90 = Duration.ofDays(90);
    assertThrows(IllegalArgumentException.class,
        () -> new DateWithRangeValidator("dd/MM/yyyy", of90, of30, "Fecha inválida"));
  }
}
