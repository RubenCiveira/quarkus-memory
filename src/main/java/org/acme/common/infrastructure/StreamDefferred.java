package org.acme.common.infrastructure;

import java.util.List;

public class StreamDefferred<T> extends StreamImpl<T> {
  
  public StreamDefferred() {
    super(true);
  }
  
  @Override
  public void next(List<T> items) {
    super.next(items);
  }

  @Override
  public void next(T item) {
    super.next(item);
  }
  
  @Override
  public void complete(T item) {
    super.complete(item);
  }

  @Override
  public void complete(List<T> items) {
    super.complete(items);
  }
  
  @Override
  protected void close() {
    super.close();
  }
}
