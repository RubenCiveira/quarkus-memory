package org.acme.common.event;

public interface AppEventListener<T> {

  T on(T kind);
}
