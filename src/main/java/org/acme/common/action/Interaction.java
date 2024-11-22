package org.acme.common.action;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Interaction {

  private final Actor actor;
  private final Connection connection;
}
