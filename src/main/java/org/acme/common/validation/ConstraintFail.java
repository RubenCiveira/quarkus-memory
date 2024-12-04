package org.acme.common.validation;

import java.util.List;

import lombok.Data;

@Data
public class ConstraintFail {
  private final String code;
  private final List<WrongValue> wrongValues;

  public ConstraintFail(String code, String field, Object wrongValue) {
    this.code = code;
    wrongValues = List.of(WrongValue.builder().field(field).wrongValue(wrongValue).build());
  }

  public ConstraintFail(String code, String field, Object wrongValue, String errorMessage) {
    this.code = code;
    wrongValues = List.of(WrongValue.builder().field(field).wrongValue(wrongValue)
        .errorMessage(errorMessage).build());
  }
}
