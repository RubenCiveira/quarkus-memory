package org.acme.common.validation.validators;

import java.util.regex.Pattern;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class EmailValidator implements Validator<String> {
  private final String errorMessage;

  public EmailValidator(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String email) {
    final Pattern emailPattern =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    if (email == null || email.isEmpty()) {
      return new ValidationResult(errorMessage);
    }
    if (!emailPattern.matcher(email).matches()) {
      return new ValidationResult(errorMessage);
    }
    return new ValidationResult();
  }
}
