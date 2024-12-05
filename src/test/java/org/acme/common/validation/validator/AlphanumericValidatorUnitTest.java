package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AlphanumericValidatorUnitTest {
  private final AlphanumericValidator complexValidator =
      new AlphanumericValidator(5, 10, true, true, true, true, "Texto inválido");
  private final AlphanumericValidator lowerValidator =
      new AlphanumericValidator(5, 10, true, false, false, false, "Texto inválido");
  private final AlphanumericValidator upperValidator =
      new AlphanumericValidator(5, 10, false, true, false, false, "Texto inválido");

  @Test
  void testValidInputs() {
    assertTrue(complexValidator.validate("Abc12").isValid());
    assertTrue(complexValidator.validate("aBcDe123").isValid());

    assertFalse(complexValidator.validate("Abc").isValid()); // Demasiado corto
    assertFalse(complexValidator.validate("Abcdefghijk").isValid()); // Demasiado largo
    assertTrue(complexValidator.validate("Abc12!").isValid()); // Contiene carácter especial

    assertFalse(complexValidator.validate(null).isValid());
    assertFalse(complexValidator.validate("").isValid());

    assertTrue(lowerValidator.validate("abcdef").isValid()); // Contiene carácter especial no
                                                             // permitido
    assertFalse(lowerValidator.validate("Abc12!").isValid()); // Contiene carácter especial no
                                                              // permitido
    assertTrue(upperValidator.validate("ABCDEF").isValid()); // Contiene carácter especial no
                                                             // permitido
    assertFalse(upperValidator.validate("Abc12!").isValid()); // Contiene carácter especial no
                                                              // permitido

    assertThrows(IllegalArgumentException.class,
        () -> new AlphanumericValidator(-1, 16, true, true, true, true, "Contraseña inválida"));
    assertThrows(IllegalArgumentException.class,
        () -> new AlphanumericValidator(22, 16, true, true, true, true, "Contraseña inválida"));
    assertThrows(IllegalArgumentException.class,
        () -> new AlphanumericValidator(1, 16, false, false, false, false, "Contraseña inválida"));

  }
}
