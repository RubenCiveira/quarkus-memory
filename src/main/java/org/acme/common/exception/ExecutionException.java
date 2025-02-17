/* @autogenerated */
package org.acme.common.exception;

import org.acme.common.validation.ExecutionFail;

public class ExecutionException extends AbstractFailsException {
  private static final long serialVersionUID = -6393019970767107758L;

  public ExecutionException(ExecutionFail fail) {
    super(fail);
  }

  public ExecutionException(String code, String errorMessage) {
    super(new ExecutionFail(code, errorMessage));
  }
}
