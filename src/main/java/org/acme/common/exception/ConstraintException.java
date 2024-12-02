package org.acme.common.exception;

import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;

public class ConstraintException extends RuntimeException {
  private static final long serialVersionUID = -6393019970767107758L;

  private final ConstraintFailList fails;

  public ConstraintException(ConstraintFailList fails) {
    this.fails = fails;
  }

  public boolean hasErrors() {
    return fails.hasErrors();
  }

  public boolean isEmpty() {
    return fails.isEmpty();
  }

  public <T extends ConstraintFail> boolean includeViolation(Class<T> type) {
    return fails.includeViolation(type);
  }

  public boolean includeCode(String code) {
    return fails.includeCode(code);
  }
}
