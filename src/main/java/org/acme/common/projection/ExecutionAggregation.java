package org.acme.common.projection;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExecutionAggregation {
  private String name;
  private String alias;
  private final List<ExecutionAggregation> selection;

}
