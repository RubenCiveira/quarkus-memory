package org.acme.features.fruit.application.usecase;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class RetrieveAllow extends Allow {

  public static RetrieveAllow from(Interaction request) {
    return RetrieveAllow.builder().allowed(true).name("list")
        .description("Read fruits is enabled by default")
        .actor(request.getActor())
        .connection(request.getConnection()).build();
  }
}
