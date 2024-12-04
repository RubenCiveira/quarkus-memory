package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PostalCodeValidatorUnitTest {
  private final PostalCodeValidator postalCodeValidator =
      new PostalCodeValidator("Código postal inválido");

  @Test
  void testValidPostalCodes() {
    assertTrue(postalCodeValidator.validate("28013").isValid()); // Madrid, España
    assertTrue(postalCodeValidator.validate("90210").isValid()); // Beverly Hills, USA

    assertFalse(postalCodeValidator.validate("1234").isValid()); // Demasiado corto
    assertFalse(postalCodeValidator.validate("123456").isValid()); // Demasiado largo
    assertFalse(postalCodeValidator.validate("12A45").isValid()); // Contiene letra

    assertFalse(postalCodeValidator.validate(null).isValid());
    assertFalse(postalCodeValidator.validate("").isValid());
  }
}
