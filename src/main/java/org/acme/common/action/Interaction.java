package org.acme.common.action;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Interaction {
  public abstract static class InteractionBuilder<C extends Interaction, B extends InteractionBuilder<C, B>> {
    public C build(Interaction prev) {
      return actor(prev.getActor()).connection(prev.getConnection()).build();
    }
  }

  @NonNull
  private final Actor actor;
  @NonNull
  private final Connection connection;

}
