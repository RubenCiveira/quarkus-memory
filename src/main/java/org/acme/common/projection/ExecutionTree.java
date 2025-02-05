/* @autogenerated */
package org.acme.common.projection;

import java.util.Map;
import java.util.Optional;

import lombok.Builder;

@Builder
public class ExecutionTree {

  private final Map<String, ExecutionNode> tree;

  public Optional<ExecutionNode> byId(String id) {
    return Optional.ofNullable(tree.get(id));
  }
}
