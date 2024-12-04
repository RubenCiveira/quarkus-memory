package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
    assertTrue(validator.validate("15/12/2024").isValid()); // Fecha dentro de 30 a 90 días
    assertFalse(validator.validate("01/12/2024").isValid()); // Antes de 30 días
    assertFalse(validator.validate("01/03/2025").isValid()); // Después de 90 días
    LocalDate minBoundary = LocalDate.now().plusDays(30);
    assertTrue(validator.validate(minBoundary.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .isValid());
    LocalDate maxBoundary = LocalDate.now().plusDays(90);
    assertTrue(validator.validate(maxBoundary.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .isValid());
    assertFalse(validator.validate("2024-12-15").isValid()); // Formato no válido
    assertFalse(validator.validate(null).isValid());
    assertFalse(validator.validate("").isValid());
  }
}
