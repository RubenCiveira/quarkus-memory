package org.acme.common.validation.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.acme.common.validation.ValidationResult;
import org.acme.common.validation.Validator;

public class DecimalNumberValidator implements Validator<String> {
  private final int maxDecimals;
  private final int precision;
  private final BigDecimal minValue;
  private final BigDecimal maxValue;
  private final String errorMessage;

  public DecimalNumberValidator(int maxDecimals, int precision, BigDecimal minValue,
      BigDecimal maxValue, String errorMessage) {
    if (maxDecimals < 0 || precision < 1 || maxDecimals > precision) {
      throw new IllegalArgumentException(
          "Parámetros inválidos para el validador de números decimales.");
    }
    this.maxDecimals = maxDecimals;
    this.precision = precision;
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.errorMessage = errorMessage;
  }

  @Override
  public ValidationResult validate(String numberStr) {
    if (numberStr == null || numberStr.isEmpty()) {
      return new ValidationResult(errorMessage);
    }
    try {
      BigDecimal number = new BigDecimal(numberStr);
      number = number.setScale(maxDecimals, RoundingMode.HALF_UP);

      // Verificar precisión total
      if (number.precision() > precision) {
        return new ValidationResult(errorMessage);
      }

      // Verificar rango
      if ((minValue != null && number.compareTo(minValue) < 0)
          || (maxValue != null && number.compareTo(maxValue) > 0)) {
        return new ValidationResult(errorMessage);
      }

      return new ValidationResult();
    } catch (NumberFormatException e) {
      return new ValidationResult(errorMessage);
    }
  }
}
