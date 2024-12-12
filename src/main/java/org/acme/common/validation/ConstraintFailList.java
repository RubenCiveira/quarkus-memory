package org.acme.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ConstraintFailList {
  private List<ConstraintFail> fails = new ArrayList<>();

  public ConstraintFailList() {
    this(new ArrayList<>());
  }

  public ConstraintFailList(String code, String field, Object wrongValue) {
    this(new ConstraintFail(code, field, wrongValue));
  }

  public ConstraintFailList(String code, String field, Object wrongValue, String errorMessage) {
    this(new ConstraintFail(code, field, wrongValue, errorMessage));
  }

  public ConstraintFailList(ConstraintFail fail) {
    this(new ArrayList<>(List.of(fail)));
  }

  public ConstraintFailList(List<ConstraintFail> fails) {
    this.fails = fails;
  }

  public Stream<ConstraintFail> getFails() {
    return fails.stream();
  }

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
