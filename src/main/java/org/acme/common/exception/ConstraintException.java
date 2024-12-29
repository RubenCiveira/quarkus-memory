package org.acme.common.exception;

import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;

public class ConstraintException extends AbstractFailsException {
  private static final long serialVersionUID = -6393019970767107758L;

  public ConstraintException(ConstraintFail fail) {
    super(fail);
  }

  public ConstraintException(ConstraintFailList fails) {
    super(fails);
  }

  public ConstraintException(String code, String field, Object wrongValue, String errorMessage) {
    super(new ConstraintFail(code, field, wrongValue, errorMessage));
  }

  public ConstraintException(String code, String field, Object wrongValue) {
    super(new ConstraintFail(code, field, wrongValue));
  }
  
}
