package org.acme.common.validation.validators;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class CreditCardValidator implements Validator<String> {
  private final String errorMessage;

  public CreditCardValidator(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String cardNumber) {
    if (cardNumber == null || cardNumber.isEmpty() || !cardNumber.matches("\\d+")) {
      return new ValidationResult(errorMessage);
    }
    if (!isValidLuhn(cardNumber)) {
      return new ValidationResult(errorMessage);
    }
    return new ValidationResult();
  }

  private boolean isValidLuhn(String cardNumber) {
    int sum = 0;
    boolean alternate = false;
    for (int i = cardNumber.length() - 1; i >= 0; i--) {
      int n = Character.getNumericValue(cardNumber.charAt(i));
      if (alternate) {
        n *= 2;
        if (n > 9) {
          n -= 9;
        }
      }
      sum += n;
      alternate = !alternate;
    }
    return (sum % 10 == 0);
  }
}
