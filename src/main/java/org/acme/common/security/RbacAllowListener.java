package org.acme.common.security;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class RbacAllowListener {
  private final Rbac rbac;

  public void checkAllow(@Observes AllowPipelineStageEvent event) {
    event.mergeMap(allow -> {
      if (allow.isAllowed()) {
        return rbac.checkAllow(event.getQuery().getActor(), event.resourceName(),
            event.actionName());
      } else {
        return CompletableFuture.completedStage(allow);
      }
    });
  }

  public void checkAllow(@Observes PropertiesPipelineStageEvent event) {
    event.mergeMap(prev -> rbac
        .inaccesibleFileds(event.getQuery().getActor(), event.resourceName(), event.viewName())
        .thenApply(res -> join(prev, res)));
  }

  private Set<String> join(Set<String> one, Set<String> two) {
    Set<String> result = new HashSet<>();
    result.addAll(one);
    result.addAll(two);
    System.out.println("Los valores finales son " + result);
    return result;
  }
}
