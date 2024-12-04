package org.acme.common.validation;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface Validator<T> {
  ValidationResult validate(T t);

  default Validator<T> and(Validator<? super T> other) {
    return obj -> {
      ValidationResult oneResult = this.validate(obj);
      ValidationResult otherResult = other.validate(obj);
      List<String> msgs = new ArrayList<>();
      msgs.addAll(oneResult.getErrors());
      msgs.addAll(otherResult.getErrors());
      return new ValidationResult(oneResult.isValid() && otherResult.isValid(), msgs);
    };
  }

  default Validator<T> or(Validator<? super T> other) {
    return obj -> {
      ValidationResult oneResult = this.validate(obj);
      ValidationResult otherResult = other.validate(obj);
      List<String> msgs = new ArrayList<>();
      msgs.addAll(oneResult.getErrors());
      msgs.addAll(otherResult.getErrors());
      return new ValidationResult(oneResult.isValid() || otherResult.isValid(), msgs);
    };
  }

  default Validator<T> negate(String message) {
    return obj -> {
      ValidationResult oneResult = this.validate(obj);
      return new ValidationResult(!oneResult.isValid(),
          oneResult.isValid() ? List.of(message) : List.of());
    };
  }
}
