package org.acme.common.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class RbacAllowListener {
  private final Rbac rbac;

  public void checkAllow(@Observes AllowPipelineStageEvent event) {
    event.map(allow -> {
      if (allow.isAllowed()) {
        return rbac.checkAllow(event.getQuery().getActor(), event.resourceName(),
            event.actionName());
      } else {
        return allow;
      }
    });
  }

  public void checkAllow(@Observes PropertiesPipelineStageEvent event) {
    event.add(rbac.inaccesibleFileds(event.getQuery().getActor(), event.resourceName(),
        event.viewName()));
  }
}
