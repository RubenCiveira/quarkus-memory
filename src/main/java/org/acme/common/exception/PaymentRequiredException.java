/* @autogenerated */
package org.acme.common.exception;

/**
 * Es un tipo de no autorizaod. En caso de existir necesidades de pago previo no cumplidas.
 */
public class PaymentRequiredException extends RuntimeException {
  private static final long serialVersionUID = 895104693033357772L;

  /**
   * Una excepcion vacia
   */
  public PaymentRequiredException() {
    super();
  }

  /**
   * Una excepcion con un mensaje y un motivo original
   * 
   * @param message el mensaje
   * @param cause el motivo original
   */
  public PaymentRequiredException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Una excepcion con un mensaje
   * 
   * @param message el mensaje
   */
  public PaymentRequiredException(final String message) {
    super(message);
  }

  /**
   * Una excepcion con un motivo original
   * 
   * @param cause
   */
  public PaymentRequiredException(final Throwable cause) {
    super(cause);
  }
}
