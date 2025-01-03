/* @autogenerated */
package org.acme.common.exception;

import java.util.stream.Stream;
import org.acme.common.validation.AbstractFail;
import org.acme.common.validation.AbstractFailList;
import org.acme.common.validation.ConstraintFail;

public abstract class AbstractFailsException extends RuntimeException {

  private static final long serialVersionUID = 261476585734235759L;
  private final AbstractFailList fails;

  public AbstractFailsException(AbstractFailList fails) {
    this.fails = fails;
  }

  public AbstractFailsException(AbstractFail fail) {
    this(new AbstractFailList(fail));
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

  public Stream<? extends AbstractFail> getFails() {
    return fails.getFails();
  }
}
