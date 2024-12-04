package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PasswordValidatorUnitTest {
  private final PasswordValidator validator =
      new PasswordValidator(8, 16, true, true, true, true, "Contraseña inválida");

  @Test
  void testValidPasswords() {
    assertTrue(validator.validate("Abcdef1!").isValid());
    assertTrue(validator.validate("A1b2C3d4!").isValid());

    assertFalse(validator.validate("abcdef1!").isValid()); // Sin mayúsculas
    assertFalse(validator.validate("ABCDEF1!").isValid()); // Sin minúsculas
    assertFalse(validator.validate("Abcdefgh").isValid()); // Sin números
    assertFalse(validator.validate("Abcdef1").isValid()); // Sin caracteres especiales
    assertFalse(validator.validate("Ab1!").isValid()); // Demasiado corta
    assertFalse(validator.validate("Abcdefghijklmnop1!").isValid()); // Demasiado larga

    assertFalse(validator.validate(null).isValid());
    assertFalse(validator.validate("").isValid());
  }
}
