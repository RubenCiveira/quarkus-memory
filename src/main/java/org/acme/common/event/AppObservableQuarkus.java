package org.acme.common.event;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppObservableQuarkus implements AppObservable {
  
  public AppObservableQuarkus(Instance<AppEventListener<?>> listeners) {
  }

  @Override
  public <T> T fire(String of, T event) {
    return event;
  }
}
