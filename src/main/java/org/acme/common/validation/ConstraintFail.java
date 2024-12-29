package org.acme.common.validation;

public class ConstraintFail extends AbstractFail {
  public ConstraintFail(String code, String field, Object wrongValue) {
    super(code, field, wrongValue);
  }

  public ConstraintFail(String code, String field, Object wrongValue, String errorMessage) {
    super(code, field, wrongValue, errorMessage);
  }
}
