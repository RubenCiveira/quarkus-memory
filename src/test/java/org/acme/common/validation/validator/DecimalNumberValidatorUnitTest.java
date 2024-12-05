package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class DecimalNumberValidatorUnitTest {
  // Máximo de 2 decimales
  // Precisión total de 5 dígitos
  // Mínimo 0.00
  // Máximo de 999.99
  private final DecimalNumberValidator validator = new DecimalNumberValidator(2, 5,
      new BigDecimal("0.00"), new BigDecimal("999.99"), "Número decimal inválido");
  private final DecimalNumberValidator noRangevalidator =
      new DecimalNumberValidator(2, 7, null, null, "Número decimal inválido");

  @Test
  void testValidNumbers() {
    assertTrue(validator.validate("123.45").isValid());
    assertTrue(validator.validate("0.99").isValid());
    assertTrue(validator.validate("999.99").isValid());

    assertFalse(validator.validate("123.456").isValid()); // Demasiados decimales
    assertFalse(validator.validate("12345").isValid()); // Excede la precisión total
    assertFalse(validator.validate("-1.00").isValid()); // Menor que el valor mínimo
    assertFalse(validator.validate("1000.00").isValid()); // Mayor que el valor máximo
    assertFalse(validator.validate("gollo").isValid()); // Mayor que el valor máximo

    assertTrue(noRangevalidator.validate("99999.00").isValid()); // Mayor que el valor máximo

    assertFalse(validator.validate(null).isValid());
    assertFalse(validator.validate("").isValid());

    assertThrows(IllegalArgumentException.class, () -> new DecimalNumberValidator(-1, 5,
        new BigDecimal("0.00"), new BigDecimal("999.99"), "Contraseña inválida"));
    assertThrows(IllegalArgumentException.class, () -> new DecimalNumberValidator(1, -5,
        new BigDecimal("0.00"), new BigDecimal("999.99"), "Contraseña inválida"));
    assertThrows(IllegalArgumentException.class, () -> new DecimalNumberValidator(12, 5,
        new BigDecimal("0.00"), new BigDecimal("999.99"), "Contraseña inválida"));

  }
}
