package org.acme.common.validation.validator;

import java.util.regex.Pattern;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class PhoneNumberValidator implements Validator<String> {
  private final String errorMessage;

  public PhoneNumberValidator(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String phoneNumber) {
    final Pattern phonePattern = Pattern.compile("^\\d{10,15}$");
    if (phoneNumber == null || phoneNumber.isEmpty()) {
      return new ValidationResult(errorMessage);
    }
    if (!phonePattern.matcher(phoneNumber).matches()) {
      return new ValidationResult(errorMessage);
    }
    return new ValidationResult();
  }
}
