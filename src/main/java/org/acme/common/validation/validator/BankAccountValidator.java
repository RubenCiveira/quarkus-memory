package org.acme.common.validation.validator;

import java.util.HashMap;
import java.util.Map;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class BankAccountValidator implements Validator<String> {
  private final boolean requireSwift;
  private final String errorMessage;
  private static final Map<String, Integer> IBAN_LENGTHS = new HashMap<>();

  static {
    IBAN_LENGTHS.put("ES", 24); // España
    // Agregar otros países según sea necesario
  }

  public BankAccountValidator(boolean requireSwift, String errorMessage) {
    this.requireSwift = requireSwift;
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String input) {
    if (input == null || input.isEmpty()) {
      return new ValidationResult(errorMessage);
    }

    String[] parts = input.split("\\s+");
    String iban = parts[0].replaceAll("\\s+", "").toUpperCase();
    String swift = parts.length > 1 ? parts[1].toUpperCase() : null;

    if (!validateIBAN(iban)) {
      return new ValidationResult(errorMessage);
    }

    if (requireSwift) {
      if (swift == null || !validateSWIFT(swift, iban)) {
        return new ValidationResult(errorMessage);
      }
    }

    return new ValidationResult();
  }

  private boolean validateIBAN(String iban) {
    if (iban.length() < 2) {
      return false;
    }

    String countryCode = iban.substring(0, 2);
    Integer expectedLength = IBAN_LENGTHS.get(countryCode);
    if (expectedLength == null || iban.length() != expectedLength) {
      return false;
    }

    String rearranged = iban.substring(4) + iban.substring(0, 4);
    StringBuilder numericIBAN = new StringBuilder();

    for (char ch : rearranged.toCharArray()) {
      if (Character.isDigit(ch)) {
        numericIBAN.append(ch);
      } else if (Character.isLetter(ch)) {
        numericIBAN.append(Character.getNumericValue(ch));
      } else {
        return false;
      }
    }

    return mod97(numericIBAN.toString()) == 1;
  }

  private int mod97(String numericIBAN) {
    String remainder = numericIBAN;
    while (remainder.length() > 2) {
      String block = remainder.substring(0, 9);
      remainder = Integer.parseInt(block) % 97 + remainder.substring(block.length());
    }
    return Integer.parseInt(remainder) % 97;
  }

  private boolean validateSWIFT(String swift, String iban) {
    if (!swift.matches("^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$")) {
      return false;
    }

    String ibanCountryCode = iban.substring(0, 2);
    String swiftCountryCode = swift.substring(4, 6);
    return ibanCountryCode.equals(swiftCountryCode);
  }
}
