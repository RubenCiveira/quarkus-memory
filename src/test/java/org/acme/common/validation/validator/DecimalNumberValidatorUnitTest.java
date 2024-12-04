package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class DecimalNumberValidatorUnitTest {
  private final DecimalNumberValidator validator = new DecimalNumberValidator(2, // Máximo de 2
                                                                                 // decimales
      5, // Precisión total de 5 dígitos
      new BigDecimal("0.00"), // Valor mínimo
      new BigDecimal("999.99"), // Valor máximo
      "Número decimal inválido");

  @Test
  void testValidNumbers() {
    assertTrue(validator.validate("123.45").isValid());
    assertTrue(validator.validate("0.99").isValid());
    assertTrue(validator.validate("999.99").isValid());

    assertFalse(validator.validate("123.456").isValid()); // Demasiados decimales
    assertFalse(validator.validate("12345").isValid()); // Excede la precisión total
    assertFalse(validator.validate("-1.00").isValid()); // Menor que el valor mínimo
    assertFalse(validator.validate("1000.00").isValid()); // Mayor que el valor máximo

    assertFalse(validator.validate(null).isValid());
    assertFalse(validator.validate("").isValid());
  }
}
