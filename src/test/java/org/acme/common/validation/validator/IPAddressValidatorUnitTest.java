package org.acme.common.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class IPAddressValidatorUnitTest {
  private final IPAddressValidator ipv4Validator =
      new IPAddressValidator(IPAddressValidator.IPVersion.IPV4, "Dirección IP inválida");

  private final IPAddressValidator ipv6Validator =
      new IPAddressValidator(IPAddressValidator.IPVersion.IPV6, "Dirección IP inválida");

  private final IPAddressValidator bothValidator =
      new IPAddressValidator(IPAddressValidator.IPVersion.BOTH, "Dirección IP inválida");

  private final IPAddressValidator subnetValidator =
      new IPAddressValidator(IPAddressValidator.IPVersion.IPV4, "192.168.1.0", "255.255.255.0",
          "Dirección IP fuera de la subred");

  @Test
  void testValidIPv4Addresses() {
    assertTrue(ipv4Validator.validate("192.168.1.1").isValid());
    assertTrue(ipv4Validator.validate("10.0.0.1").isValid());

    assertFalse(ipv4Validator.validate("256.256.256.256").isValid());
    assertFalse(ipv4Validator.validate("192.168.1").isValid());

    assertTrue(ipv6Validator.validate("2001:0db8:85a3:0000:0000:8a2e:0370:7334").isValid());

    assertFalse(ipv6Validator.validate("2001:db8::85a3::8a2e:370:7334").isValid());

    assertTrue(bothValidator.validate("192.168.1.1").isValid());
    assertTrue(bothValidator.validate("2001:0db8:85a3:0000:0000:8a2e:0370:7334").isValid());

    assertFalse(bothValidator.validate("invalid_ip").isValid());

    assertTrue(subnetValidator.validate("192.168.1.100").isValid());
  }

}
