package org.acme.features.fruit.application.usecase;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class DeleteAllow extends Allow {

  public static DeleteAllow from(Interaction request) {
    return DeleteAllow.builder().allowed(false).name("create")
        .description("Create fruits is disabled by default")
        .actor(request.getActor())
        .connection(request.getConnection()).build();
  }
}
