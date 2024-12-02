package org.acme.common.validation;

import java.util.ArrayList;
import java.util.List;

public class ConstraintFailList {
  private List<ConstraintFail> fails = new ArrayList<>();

  public boolean hasErrors() {
    return !fails.isEmpty();
  }

  public boolean isEmpty() {
    return fails.isEmpty();
  }

  public void add(ConstraintFail fail) {
    fails.add(fail);
  }

  public <T extends ConstraintFail> boolean includeViolation(Class<T> type) {
    return fails.stream().anyMatch(fail -> type.isAssignableFrom(fail.getClass()));
  }

  public boolean includeCode(String code) {
    return fails.stream().anyMatch(fail -> code.equals(fail.getCode()));
  }
}
