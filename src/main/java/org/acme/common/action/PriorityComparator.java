package org.acme.common.action;

import java.util.Comparator;
import jakarta.annotation.Priority;

public class PriorityComparator<T> implements Comparator<T> {

  @Override
  public int compare(T one, T other) {
    Priority p1 = one.getClass().getAnnotation(Priority.class);
    Priority p2 = other.getClass().getAnnotation(Priority.class);
    return Integer.compare( p1==null ? 0 : p1.value(), p2==null ? 0 : p2.value());
  }
}
