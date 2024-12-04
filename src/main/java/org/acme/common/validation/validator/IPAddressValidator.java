package org.acme.common.validation.validator;

import java.util.regex.Pattern;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class IPAddressValidator implements Validator<String> {

  public enum IPVersion {
    IPV4, IPV6, BOTH
  }

  private final IPVersion ipVersion;
  private final String networkAddress;
  private final String subnetMask;
  private final String errorMessage;

  public IPAddressValidator(IPVersion ipVersion, String errorMessage) {
    this(ipVersion, null, null, errorMessage);
  }

  public IPAddressValidator(IPVersion ipVersion, String networkAddress, String subnetMask,
      String errorMessage) {
    this.ipVersion = ipVersion;
    this.networkAddress = networkAddress;
    this.subnetMask = subnetMask;
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String ipAddress) {
    if (ipAddress == null || ipAddress.isEmpty()) {
      return new ValidationResult(errorMessage);
    }

    final Pattern ipv4Pattern = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
        + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
        + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    final Pattern ipv6Pattern = Pattern.compile("^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");


    boolean isValidFormat = false;
    if (ipVersion == IPVersion.IPV4 || ipVersion == IPVersion.BOTH) {
      isValidFormat = ipv4Pattern.matcher(ipAddress).matches();
    }
    if (!isValidFormat && (ipVersion == IPVersion.IPV6 || ipVersion == IPVersion.BOTH)) {
      isValidFormat = ipv6Pattern.matcher(ipAddress).matches();
    }

    if (!isValidFormat) {
      return new ValidationResult(errorMessage);
    }

    if (networkAddress != null && subnetMask != null) {
      if (!isInSubnet(ipAddress, networkAddress, subnetMask)) {
        return new ValidationResult(errorMessage);
      }
    }
    return new ValidationResult();
  }

  private boolean isInSubnet(String ipAddress, String networkAddress, String subnetMask) {
    try {
      byte[] ip = toBytes(ipAddress);
      byte[] net = toBytes(networkAddress);
      byte[] mask = toBytes(subnetMask);

      for (int i = 0; i < ip.length; i++) {
        if ((ip[i] & mask[i]) != (net[i] & mask[i])) {
          return false;
        }
      }
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  private byte[] toBytes(String ipAddress) {
    String[] parts = ipAddress.split("\\.");
    if (parts.length != 4) {
      throw new IllegalArgumentException("Dirección IP inválida");
    }
    byte[] bytes = new byte[4];
    for (int i = 0; i < 4; i++) {
      int num = Integer.parseInt(parts[i]);
      if (num < 0 || num > 255) {
        throw new IllegalArgumentException("Dirección IP inválida");
      }
      bytes[i] = (byte) num;
    }
    return bytes;
  }
}
