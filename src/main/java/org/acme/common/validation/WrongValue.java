package org.acme.common.validation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WrongValue {
  private final String field;
  private final Object wrongValue;
  private final Object exceptedValue;
}
