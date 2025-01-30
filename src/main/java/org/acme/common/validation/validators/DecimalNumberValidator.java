package org.acme.common.validation.validators;

import java.math.BigDecimal;

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
      // number = number.setScale(maxDecimals, RoundingMode.HALF_UP);
      if (number.scale() > maxDecimals) {
        return new ValidationResult(errorMessage);
      }
      // Verificar precisión total
      if (number.precision() > precision) {
        return new ValidationResult(errorMessage);
      }
      boolean isLower = minValue != null && number.compareTo(minValue) < 0;
      boolean isUpper = maxValue != null && number.compareTo(maxValue) > 0;
      // Verificar rango
      if (isLower || isUpper) {
        return new ValidationResult(errorMessage);
      }

      return new ValidationResult();
    } catch (NumberFormatException e) {
      return new ValidationResult(errorMessage);
    }
  }
}
