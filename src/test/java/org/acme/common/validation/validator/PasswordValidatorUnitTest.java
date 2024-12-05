package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PasswordValidatorUnitTest {
  private final PasswordValidator complexPassword =
      new PasswordValidator(8, 16, true, true, true, true, "Contraseña inválida");
  private final PasswordValidator trivialPassword =
      new PasswordValidator(8, 16, false, false, false, false, "Contraseña inválida");

  @Test
  void testValidPasswords() {
    assertTrue(complexPassword.validate("Abcdef1!").isValid());
    assertTrue(complexPassword.validate("A1b2C3d4!").isValid());

    assertFalse(complexPassword.validate("abcdef1!").isValid()); // Sin mayúsculas
    assertFalse(complexPassword.validate("ABCDEF1!").isValid()); // Sin minúsculas
    assertFalse(complexPassword.validate("Abcdefgh").isValid()); // Sin números
    assertFalse(complexPassword.validate("Abcdef1").isValid()); // Sin caracteres especiales
    assertFalse(complexPassword.validate("Ab1!").isValid()); // Demasiado corta
    assertFalse(complexPassword.validate("Abcdefghijklmnop1!").isValid()); // Demasiado larga

    assertFalse(complexPassword.validate(null).isValid());
    assertFalse(complexPassword.validate("").isValid());

    assertThrows(IllegalArgumentException.class,
        () -> new PasswordValidator(-1, 16, true, true, true, true, "Contraseña inválida"));
    assertThrows(IllegalArgumentException.class,
        () -> new PasswordValidator(22, 16, true, true, true, true, "Contraseña inválida"));

    assertFalse(trivialPassword.validate("abc").isValid()); //
    assertFalse(trivialPassword.validate("Abc").isValid()); // Sin mayúsculas
  }
}
