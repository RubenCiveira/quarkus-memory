package org.acme.common.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AbstractFailList {
  private List<AbstractFail> fails = new ArrayList<>();

  public AbstractFailList() {
    this(new ArrayList<>());
  }

  public AbstractFailList(AbstractFail fail) {
    this(new ArrayList<>(List.of(fail)));
  }

  public AbstractFailList(List<? extends AbstractFail> fails) {
    this.fails = new ArrayList<>(fails);
  }

  public Stream<? extends AbstractFail> getFails() {
    return fails.stream();
  }

  public boolean hasErrors() {
    return !fails.isEmpty();
  }

  public boolean isEmpty() {
    return fails.isEmpty();
  }

  public void add(AbstractFail fail) {
    fails.add(fail);
  }

  public <T extends AbstractFailList> void add(T list) {
    fails.addAll(list.getFails().toList());
  }

  public <T extends AbstractFail> boolean includeViolation(Class<T> type) {
    return fails.stream().anyMatch(fail -> type.isAssignableFrom(fail.getClass()));
  }

  public boolean includeCode(String code) {
    return fails.stream().anyMatch(fail -> code.equals(fail.getCode()));
  }
}
