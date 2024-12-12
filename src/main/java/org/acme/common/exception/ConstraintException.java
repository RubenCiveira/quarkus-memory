package org.acme.common.exception;

import java.util.stream.Stream;

import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;

public class ConstraintException extends RuntimeException {
  private static final long serialVersionUID = -6393019970767107758L;

  private final ConstraintFailList fails;

  public ConstraintException(ConstraintFailList fails) {
    this.fails = fails;
  }

  public ConstraintException(ConstraintFail fail) {
    this(new ConstraintFailList(fail));
  }

  public ConstraintException(String code, String field, Object wrongValue) {
    this(new ConstraintFailList(code, field, wrongValue));
  }

  public ConstraintException(String code, String field, Object wrongValue, String errorMessage) {
    this(new ConstraintFailList(code, field, wrongValue, errorMessage));
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

  public Stream<ConstraintFail> getFails() {
    return fails.getFails();
  }
}
