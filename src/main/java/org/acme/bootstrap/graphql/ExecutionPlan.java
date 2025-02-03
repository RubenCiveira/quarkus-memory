package org.acme.bootstrap.graphql;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExecutionPlan {
  private final ExecutionNode node;
  private final Map<String, String> selection;

}
