package org.acme.common.security.scope;

import java.util.ArrayList;
import java.util.List;

public class ScopeAllowList {

  private final List<ScopeAllow> list = new ArrayList<>();

  public void add(ScopeAllow scope) {
    list.add(scope);
  }

  public boolean allowed(String resource, String action) {
    return list.stream().anyMatch(sa -> sa.match(resource, action));
  }

}
