package org.acme.common.event;

public interface AppObservable {

  <T> T fire(String of, T event);
}
