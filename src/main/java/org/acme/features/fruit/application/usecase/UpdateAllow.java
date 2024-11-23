package org.acme.features.fruit.application.usecase;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class UpdateAllow extends Allow {

  public static UpdateAllow from(Interaction request) {
    return UpdateAllow.builder().allowed(false).name("create")
        .description("Create fruits is disabled by default")
        .actor(request.getActor())
        .connection(request.getConnection()).build();
  }
}
