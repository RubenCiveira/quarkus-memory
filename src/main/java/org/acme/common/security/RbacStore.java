/* @autogenerated */
package org.acme.common.security;

import org.acme.common.security.scope.FieldDescription;
import org.acme.common.security.scope.FieldGrantList;
import org.acme.common.security.scope.ResourceDescription;
import org.acme.common.security.scope.ScopeAllowList;
import org.acme.common.security.scope.ScopeDescription;

public interface RbacStore {

  boolean isActive();

  void registerResourceField(ResourceDescription resource, FieldDescription field);

  void registerResourceAction(ResourceDescription resource, ScopeDescription action);

  ScopeAllowList checkRoleScopes(Actor actor);

  FieldGrantList checkRoleProperties(Actor actor);
}
