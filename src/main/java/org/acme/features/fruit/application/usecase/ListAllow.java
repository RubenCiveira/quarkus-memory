package org.acme.features.fruit.application.usecase;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public class ListAllow extends Allow {

  public static ListAllow from(Interaction request) {
    return ListAllow.builder().allowed(true).name("list")
        .description("Read fruits is enabled by default").actor(request.getActor())
        .connection(request.getConnection()).build();
  }
}
