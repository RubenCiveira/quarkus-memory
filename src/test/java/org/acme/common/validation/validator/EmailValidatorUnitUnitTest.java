package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class EmailValidatorUnitUnitTest {
  private final EmailValidator emailValidator = new EmailValidator("Invalid email address");

  @Test
  void testValidEmails() {
    assertTrue(emailValidator.validate("test@example.com").isValid());
    assertTrue(emailValidator.validate("user.name+tag+sorting@example.com").isValid());
    assertTrue(emailValidator.validate("another.email@subdomain.example.com").isValid());

    assertFalse(emailValidator.validate("plainaddress").isValid());
    assertFalse(emailValidator.validate("@missingusername.com").isValid());
    assertFalse(emailValidator.validate("username@.com").isValid());

    assertFalse(emailValidator.validate(null).isValid());
    assertFalse(emailValidator.validate("").isValid());
  }
}
