package org.acme.features.market.area.application.usecase.retrieve.event;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowPipelineStageEvent;
import org.acme.features.market.area.domain.model.AreaRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AreaRetrieveAllowPipelineStageEvent extends AllowPipelineStageEvent {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param reference
   * @param allowed
   * @return
   */
  public static AreaRetrieveAllowPipelineStageEvent build(final Interaction query,
      final Optional<AreaRef> reference, final boolean allowed) {
    return build(query, reference, allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param reference
   * @param allowed
   * @param reason
   * @return
   */
  public static AreaRetrieveAllowPipelineStageEvent build(final Interaction query,
      final Optional<AreaRef> reference, final boolean allowed, final String reason) {
    return AreaRetrieveAllowPipelineStageEvent.builder().query(query)
        .reference(reference.orElse(null)).detail(CompletableFuture
            .completedFuture(Allow.builder().allowed(allowed).description(reason).build()))
        .build();
  }

  /**
   * @autogenerated EntityGenerator
   */
  private final AreaRef reference;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String actionName() {
    return "retrieve";
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<AreaRef> getReference() {
    return Optional.ofNullable(reference);
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String resourceName() {
    return "area";
  }
}
