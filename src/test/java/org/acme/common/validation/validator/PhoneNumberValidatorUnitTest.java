package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PhoneNumberValidatorUnitTest {
  private final PhoneNumberValidator phoneValidator =
      new PhoneNumberValidator("Número de teléfono inválido");

  @Test
  void testValidPhoneNumbers() {
    assertTrue(phoneValidator.validate("1234567890").isValid());
    assertTrue(phoneValidator.validate("123456789012345").isValid());

    assertFalse(phoneValidator.validate("12345").isValid()); // Demasiado corto
    assertFalse(phoneValidator.validate("1234567890123456").isValid()); // Demasiado largo
    assertFalse(phoneValidator.validate("12345abcde").isValid()); // Contiene letras

    assertFalse(phoneValidator.validate(null).isValid());
    assertFalse(phoneValidator.validate("").isValid());
  }
}
