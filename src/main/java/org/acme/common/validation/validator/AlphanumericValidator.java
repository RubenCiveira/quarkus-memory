package org.acme.common.validation.validator;

import java.util.regex.Pattern;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class AlphanumericValidator implements Validator<String> {
  private final int minLength;
  private final int maxLength;
  private final boolean allowLowercase;
  private final boolean allowUppercase;
  private final boolean allowNumbers;
  private final boolean allowSpecialChars;
  private final String errorMessage;
  private final Pattern pattern;

  public AlphanumericValidator(int minLength, int maxLength, boolean allowLowercase,
      boolean allowUppercase, boolean allowNumbers, boolean allowSpecialChars,
      String errorMessage) {
    if (minLength < 0 || maxLength < minLength) {
      throw new IllegalArgumentException("Parámetros de longitud inválidos.");
    }
    this.minLength = minLength;
    this.maxLength = maxLength;
    this.allowLowercase = allowLowercase;
    this.allowUppercase = allowUppercase;
    this.allowNumbers = allowNumbers;
    this.allowSpecialChars = allowSpecialChars;
    this.errorMessage = errorMessage;
    this.pattern = Pattern.compile(buildRegex());
  }

  private String buildRegex() {
    StringBuilder regex = new StringBuilder("^[");

    if (allowLowercase) {
      regex.append("a-z");
    }
    if (allowUppercase) {
      regex.append("A-Z");
    }
    if (allowNumbers) {
      regex.append("0-9");
    }
    if (allowSpecialChars) {
      regex.append("\\p{Punct}");
    }

    regex.append("]{").append(minLength).append(",").append(maxLength).append("}$");
    return regex.toString();
  }

  @Override
  public ValidationResult validate(String input) {
    if (input == null || !pattern.matcher(input).matches()) {
      return new ValidationResult(errorMessage);
    }
    return new ValidationResult();
  }
}
