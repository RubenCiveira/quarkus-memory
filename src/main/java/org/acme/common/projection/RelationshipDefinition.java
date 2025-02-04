package org.acme.common.projection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationshipDefinition {
  private final boolean list;
  private final String id;
  private final String on;
  private final String url;
  private final String method;
  private final String batchParam;
  private final String referenceField;
}
