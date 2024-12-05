package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AlphanumericValidatorUnitTest {
  private final AlphanumericValidator validator =
      new AlphanumericValidator(5, 10, true, true, true, false, "Texto inválido");

  @Test
  void testValidInputs() {
    assertTrue(validator.validate("Abc12").isValid());
    assertTrue(validator.validate("aBcDe123").isValid());

    assertFalse(validator.validate("Abc").isValid()); // Demasiado corto
    assertFalse(validator.validate("Abcdefghijk").isValid()); // Demasiado largo
    assertFalse(validator.validate("Abc12!").isValid()); // Contiene carácter especial no permitido

    assertFalse(validator.validate(null).isValid());
    assertFalse(validator.validate("").isValid());
    
    assertThrows(IllegalArgumentException.class, () ->  new AlphanumericValidator(-1, 16, true, true, true, true, "Contraseña inválida"));
    assertThrows(IllegalArgumentException.class, () ->  new AlphanumericValidator(22, 16, true, true, true, true, "Contraseña inválida"));

  }
}
