package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BankAccountValidatorUnitTest {
  private final BankAccountValidator validatorWithSwift =
      new BankAccountValidator(true, "Datos bancarios inválidos");
  private final BankAccountValidator validatorWithoutSwift =
      new BankAccountValidator(false, "Datos bancarios inválidos");

  @Test
  void testValidIBANWithSWIFT() {
    assertTrue(validatorWithSwift.validate("ES9121000418450200051332 BBVAESMMXXX").isValid());
    assertTrue(validatorWithoutSwift.validate("ES9121000418450200051332").isValid());
    assertFalse(validatorWithoutSwift.validate("ES9121000418450200051333").isValid()); // Dígito de
                                                                                       // control
                                                                                       // incorrecto
    assertFalse(validatorWithSwift.validate("ES9121000418450200051332 INVALIDSWIFT").isValid()); // SWIFT
                                                                                                 // inválido
    assertFalse(validatorWithSwift.validate("ES9121000418450200051332 DEUTDEFF").isValid()); // SWIFT
                                                                                             // no

    assertFalse(validatorWithoutSwift.validate("U").isValid()); // Dígito de
    assertFalse(validatorWithoutSwift.validate("ES91210004").isValid()); // Dígito de
    assertFalse(validatorWithoutSwift.validate("ES9121000#18450200051332").isValid());
    assertFalse(validatorWithoutSwift.validate("FR91210004").isValid()); // Dígito de


    assertFalse(validatorWithSwift.validate("ES9121000418450200051332").isValid()); // SWIFT

    assertFalse(validatorWithoutSwift.validate(null).isValid());
    assertFalse(validatorWithoutSwift.validate("").isValid());
  }
}
