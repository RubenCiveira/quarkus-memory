package org.acme.common.security.rcab;

import java.util.concurrent.CompletionStage;

import org.acme.common.security.Actor;
import org.acme.common.security.RbacStore;
import org.acme.common.security.scope.FieldDescription;
import org.acme.common.security.scope.FieldGrantList;
import org.acme.common.security.scope.ResourceDescription;
import org.acme.common.security.scope.ScopeAllowList;
import org.acme.common.security.scope.ScopeDescription;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@IfBuildProperty(name = "mp.rcab.model", stringValue = "keycloack")
@RequiredArgsConstructor
public class Keycloack implements RbacStore {
  private final String KIND = "keycloack";
  private final @ConfigProperty(name = "mp.rcab.model") String model;

  @Override
  public boolean isActive() {
    System.out.println("MIRANDO CON " + KIND);
    return KIND.equals(model);
  }

  @Override
  public void registerResourceAction(ResourceDescription resource, ScopeDescription action) {
    System.err.println("KEYCLOACK MODE FOR SCOPE OF " + resource + " on " + action);
  }

  @Override
  public void registerResourceField(ResourceDescription resource, FieldDescription field) {
    System.err.println("KEYCLOACK MODE FOR FIELD OF " + resource + " as " + field);
  }

  @Override
  public CompletionStage<ScopeAllowList> checkRoleScopes(Actor actor) {
    return null;
  }

  @Override
  public CompletionStage<FieldGrantList> checkRoleProperties(Actor actor) {
    return null;
  }

}
