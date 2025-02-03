package org.acme.bootstrap.graphql;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExecutionParam {
  private String in;
  private String name;
  private boolean required;
}
