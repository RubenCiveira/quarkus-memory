package org.acme.common.action;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Interaction {
  public abstract static class InteractionBuilder<C extends Interaction, B extends InteractionBuilder<C, B>> {
    public C build(Interaction prev) {
      return build(prev.getActor(), prev.getConnection());
    }

    public C build(Actor actor, Connection connection) {
      return actor(actor).connection(connection).build();
    }
  }

  private final Actor actor;
  private final Connection connection;
}
