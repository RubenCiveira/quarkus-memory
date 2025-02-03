package org.acme.bootstrap.graphql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecutionNode {
  private final String server;
  private final String endpoint;
  private final String method;
  private final boolean list;
  private final Map<String, ExecutionParam> parameters = new HashMap<>();
  private final List<ExecutionNode> children = new ArrayList<>();

  public void addChild(ExecutionNode node) {
    children.add(node);
  }

  public void setParameter(String name, ExecutionParam value) {
    parameters.put(name, value);
  }

  public String target(Map<String, String> params) {
    String output = server + endpoint;
    for (Entry<String, String> entry : params.entrySet()) {
      output.replace("{" + entry.getKey() + "}", entry.getValue());
    }
    return output;
  }
}
