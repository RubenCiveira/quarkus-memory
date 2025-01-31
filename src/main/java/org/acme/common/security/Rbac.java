package org.acme.common.security;

import java.util.ArrayList;
import java.util.List;

import org.acme.common.security.scope.FieldDescription;
import org.acme.common.security.scope.ResourceDescription;
import org.acme.common.security.scope.ScopeDescription;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class Rbac {
  private final Instance<RbacStore> manager;

  public void registerResourceField(ResourceDescription resource, FieldDescription field) {
    manager.forEach(instance -> {
      if (instance.isActive()) {
        instance.registerResourceField(resource, field);
      }
    });
  }

  public void registerResourceAction(ResourceDescription resource, ScopeDescription action) {
    manager.forEach(instance -> {
      if (instance.isActive()) {
        instance.registerResourceAction(resource, action);
      }
    });
  }

  public Allow checkAllow(Actor actor, String resource, String action) {
    List<RbacStore> processors = new ArrayList<>();
    manager.forEach(instance -> {
      if (instance.isActive()) {
        processors.add(instance);
      }
    });
    return processors.stream().findFirst()
        .<Allow>map(store -> Allow.builder()
            .allowed(store.checkRoleScopes(actor).allowed(resource, action)).build())
        .orElseGet(() -> Allow.builder().allowed(true).build());
  }

  public List<String> inaccesibleFileds(Actor actor, String resource, String view) {
    List<RbacStore> processors = new ArrayList<>();
    manager.forEach(instance -> {
      if (instance.isActive()) {
        processors.add(instance);
      }
    });
    return processors.stream().findFirst()
        .map(store -> store.checkRoleProperties(actor).innacesiblesFor(resource, view))
        .orElseGet(List::of);
  }
}
