package org.acme.common.projection;

import java.util.Map;
import java.util.Optional;
import lombok.Builder;

@Builder
public class ExecutionTree {

  private final Map<String, Map<String, ExecutionNode>> nodes;
  private final Map<String, ExecutionNode> tree;

  public Optional<ExecutionNode> byRequest(String rootEndpoint, Map<String, String> params) {
    return nodes.get(rootEndpoint).values().stream().filter(node -> hasPath(params, node))
        .sorted(this::comparePathParams).findFirst();
  }

  public Optional<ExecutionNode> byId(String id) {
    return Optional.ofNullable( tree.get(id) );
  }

  private boolean hasPath(Map<String, String> params, ExecutionNode node) {
    boolean hasAllPassed = node.getParameters().keySet().containsAll(params.keySet());
    boolean hasAllRequired = params.keySet().containsAll(node.getParameters().values().stream()
        .filter(ExecutionParam::isRequired).map(ExecutionParam::getName).toList());
    return hasAllPassed && hasAllRequired;
  }

  private int comparePathParams(ExecutionNode one, ExecutionNode other) {
    return countPathParams(other) - countPathParams(one);
  }

  private int countPathParams(ExecutionNode one) {
    return (int) one.getParameters().values().stream()
        .filter(kind -> kind.getIn().equals("path")).count();
  }
}
