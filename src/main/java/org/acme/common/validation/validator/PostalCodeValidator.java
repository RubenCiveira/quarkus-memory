package org.acme.common.validation.validator;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class PostalCodeValidator implements Validator<String> {
  private final String errorMessage;

  public PostalCodeValidator(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String postalCode) {
    if (postalCode == null || postalCode.isEmpty()) {
      return new ValidationResult(errorMessage);
    }
    if (postalCode.length() != 5) {
      return new ValidationResult(errorMessage);
    }
    for (char c : postalCode.toCharArray()) {
      if (!Character.isDigit(c)) {
        return new ValidationResult(errorMessage);
      }
    }
    return new ValidationResult();
  }
}

