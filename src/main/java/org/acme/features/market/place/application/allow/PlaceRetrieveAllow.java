package org.acme.features.market.place.application.allow;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.security.Allow;
import org.acme.features.market.place.domain.model.PlaceRef;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PlaceRetrieveAllow {

  /**
   * @autogenerated EntityGenerator
   * @param reference
   * @param allowed
   * @return
   */
  public static PlaceRetrieveAllow build(final Optional<PlaceRef> reference,
      final boolean allowed) {
    return build(reference, allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param reference
   * @param allowed
   * @param reason
   * @return
   */
  public static PlaceRetrieveAllow build(final Optional<PlaceRef> reference, final boolean allowed,
      final String reason) {
    return PlaceRetrieveAllow.builder().reference(reference.orElse(null)).detail(CompletableFuture
        .completedFuture(Allow.builder().allowed(allowed).description(reason).build())).build();
  }

  /**
   * @autogenerated EntityGenerator
   */
  private final PlaceRef reference;

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private CompletionStage<Allow> detail;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<PlaceRef> getReference() {
    return Optional.ofNullable(reference);
  }
}
