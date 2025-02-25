/* @autogenerated */
package org.acme.common.validation;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * A store to save the information related with a constraint violation in a domain object.
 */
@RegisterForReflection
public class ExecutionFail extends AbstractFail {

  public ExecutionFail(final String code, final String description) {
    super(code, null, null, description);
  }
}
