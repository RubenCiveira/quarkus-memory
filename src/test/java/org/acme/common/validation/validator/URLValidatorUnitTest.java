package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class URLValidatorUnitTest {
  private final URLValidator urlValidator = new URLValidator("URL inválida");

  @Test
  void testValidURLs() {
    assertTrue(urlValidator.validate("http://example.com").isValid());
    assertTrue(urlValidator.validate("https://www.example.com/path?query=123").isValid());
    assertTrue(urlValidator.validate("ftp://ftp.example.com/file.txt").isValid());

    assertFalse(urlValidator.validate("htp://invalid-url").isValid()); // Esquema incorrecto
    assertFalse(urlValidator.validate("http://").isValid()); // Falta dominio
    assertFalse(urlValidator.validate("http://example .com").isValid()); // Espacio en el dominio

    assertFalse(urlValidator.validate(null).isValid());
    assertFalse(urlValidator.validate("").isValid());
  }
}
