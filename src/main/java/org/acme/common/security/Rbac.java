package org.acme.common.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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

  public CompletionStage<Allow> checkAllow(Actor actor, String resource, String action) {
    List<RbacStore> processors = new ArrayList<>();
    manager.forEach(instance -> {
      if (instance.isActive()) {
        processors.add(instance);
      }
    });
    return manager.stream().findFirst()
        .<CompletionStage<Allow>>map(store -> store.checkRoleScopes(actor)
            .thenApply(scopes -> Allow.builder().allowed(scopes.allowed(resource, action)).build()))
        .orElseGet(() -> CompletableFuture.completedStage(Allow.builder().allowed(false).build()));
  }

  public CompletionStage<Set<String>> inaccesibleFileds(Actor actor, String resource, String view) {
    List<RbacStore> processors = new ArrayList<>();
    manager.forEach(instance -> {
      if (instance.isActive()) {
        processors.add(instance);
      }
    });
    return manager.stream().findFirst().<CompletionStage<Set<String>>>map(
        store -> store.checkRoleProperties(actor).thenApply(scopes -> {
          System.out.println("los valores que vienen del store son: " + scopes);
          System.out.println("Consultado la vista " + view);
          Set<String> set = new HashSet<>();
          set.addAll(scopes.innacesiblesFor(resource, view));
          return set;
        })).orElseGet(() -> CompletableFuture.completedStage(Set.of()));
  }
}
