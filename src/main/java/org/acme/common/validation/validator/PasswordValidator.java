package org.acme.common.validation.validator;

import java.util.regex.Pattern;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class PasswordValidator implements Validator<String> {
  private final int minLength;
  private final int maxLength;
  private final boolean requireLowercase;
  private final boolean requireUppercase;
  private final boolean requireNumbers;
  private final boolean requireSpecialChars;
  private final String errorMessage;
  private final Pattern pattern;

  public PasswordValidator(int minLength, int maxLength, boolean requireLowercase,
      boolean requireUppercase, boolean requireNumbers, boolean requireSpecialChars,
      String errorMessage) {
    if (minLength < 0 || maxLength < minLength) {
      throw new IllegalArgumentException("Parámetros de longitud inválidos.");
    }
    this.minLength = minLength;
    this.maxLength = maxLength;
    this.requireLowercase = requireLowercase;
    this.requireUppercase = requireUppercase;
    this.requireNumbers = requireNumbers;
    this.requireSpecialChars = requireSpecialChars;
    this.errorMessage = errorMessage;
    this.pattern = Pattern.compile(buildRegex());
  }

  private String buildRegex() {
    StringBuilder regex = new StringBuilder("^(?=.*[a-z])");

    if (requireLowercase) {
      regex.append("(?=.*[a-z])");
    }
    if (requireUppercase) {
      regex.append("(?=.*[A-Z])");
    }
    if (requireNumbers) {
      regex.append("(?=.*\\d)");
    }
    if (requireSpecialChars) {
      regex.append("(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])");
    }
    regex.append(".{").append(minLength).append(",").append(maxLength).append("}$");
    return regex.toString();
  }

  @Override
  public ValidationResult validate(String password) {
    if (password == null || !pattern.matcher(password).matches()) {
      return new ValidationResult(errorMessage);
    }
    return new ValidationResult();
  }
}
