/* @autogenerated */
package org.acme.common.projection;

import java.util.Map;
import java.util.Map.Entry;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ExecutionNode {
  @NonNull
  private final String server;
  @NonNull
  private final String endpoint;
  @NonNull
  private final String method;
  private final boolean list;
  @NonNull
  private final Map<String, RelationshipDefinition> relations;

  public void addRelation(String property, RelationshipDefinition definition) {
    relations.put(property, definition);
  }

  public String target(Map<String, String> params) {
    String output = server + endpoint;
    for (Entry<String, String> entry : params.entrySet()) {
      output.replace("{" + entry.getKey() + "}", entry.getValue());
    }
    return output;
  }
}
