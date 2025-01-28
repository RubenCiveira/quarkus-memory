package org.acme.common.security.scope;

import java.util.ArrayList;
import java.util.List;

public class FieldGrantList {
  private final List<FieldGrant> fields = new ArrayList<>();

  public void add(FieldGrant field) {
    fields.add(field);
  }

  public List<String> innacesiblesFor(String resource, String view) {
    return fields.stream().filter(fg -> fg.match(resource, view)).map(FieldGrant::getName).toList();
  }
}
