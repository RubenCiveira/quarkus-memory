package org.acme.common.validation.validator;

import java.util.regex.Pattern;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class IPAddressValidator implements Validator<String> {

  public enum IPVersion {
    IPV4, IPV6, BOTH
  }

  private final IPVersion ipVersion;
  private final byte[] networkAddress;
  private final byte[] subnetMask;
  private final String errorMessage;

  public IPAddressValidator(IPVersion ipVersion, String errorMessage) {
    this(ipVersion, null, null, errorMessage);
  }

  public IPAddressValidator(IPVersion ipVersion, String networkAddress, String subnetMask,
      String errorMessage) {
    this.ipVersion = ipVersion;
    this.errorMessage = errorMessage;
    if (networkAddress != null && subnetMask != null) {
      this.networkAddress = toBytes(networkAddress);
      this.subnetMask = toBytes(subnetMask);
    } else if (networkAddress != null || subnetMask != null) {
      throw new IllegalArgumentException("If a network is indicated, a subnet is required.");
    } else {
      this.networkAddress = null;
      this.subnetMask = null;
    }
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

    if (networkAddress != null && !isInSubnet(ipAddress)) {
      return new ValidationResult(errorMessage);
    }
    return new ValidationResult();
  }

  private boolean isInSubnet(String ipAddress) {
    byte[] ip = toBytes(ipAddress);

    for (int i = 0; i < ip.length; i++) {
      if ((ip[i] & subnetMask[i]) != (networkAddress[i] & subnetMask[i])) {
        return false;
      }
    }
    return true;
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
