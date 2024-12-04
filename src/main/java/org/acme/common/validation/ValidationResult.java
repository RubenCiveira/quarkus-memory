package org.acme.common.validation;

import java.util.List;

public class ValidationResult {
  private final boolean isValid;
  private final List<String> errors;

  public ValidationResult() {
    this(true, List.of());
  }

  public ValidationResult(String message) {
    this(false, List.of(message));
  }

  public ValidationResult(boolean isValid, List<String> errors) {
    this.isValid = isValid;
    this.errors = errors;
  }

  public boolean isValid() {
    return isValid;
  }

  public List<String> getErrors() {
    return errors;
  }
}
