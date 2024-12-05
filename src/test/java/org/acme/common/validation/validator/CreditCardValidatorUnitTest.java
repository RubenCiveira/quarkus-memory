package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CreditCardValidatorUnitTest {
  private final CreditCardValidator validator =
      new CreditCardValidator("Número de tarjeta inválido");

  @Test
  void testValidCreditCardNumbers() {
    assertTrue(validator.validate("4532015112830366").isValid()); // Visa
    assertTrue(validator.validate("6011514433546201").isValid()); // Discover

    assertFalse(validator.validate("4532015112830367").isValid()); // Número inválido
    assertFalse(validator.validate("1234567812345678").isValid()); // Número inválido

    assertFalse(validator.validate(null).isValid());
    assertFalse(validator.validate("").isValid());

    assertFalse(validator.validate("4532a15112830366").isValid()); // Contiene letra
    assertFalse(validator.validate("6011-5144-3354-6201").isValid()); // Contiene guiones
  }
}
