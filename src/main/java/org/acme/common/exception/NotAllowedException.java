/* @autogenerated */
package org.acme.common.exception;

/**
 * An exception will be thrown when a non autorized user try to execute an operation. In a normal
 * case, the programer use to test if there is a restriction, and avoid to execute the task.
 */
public class NotAllowedException extends RuntimeException {
  /**
   * A number to help Serialization.
   */
  private static final long serialVersionUID = -1693325582870899954L;

  /**
   * Create a new exception by a restriction.
   * 
   * @param accessRestriction the access restriction
   */
  public NotAllowedException(final String message) {
    super(message);
  }
}
