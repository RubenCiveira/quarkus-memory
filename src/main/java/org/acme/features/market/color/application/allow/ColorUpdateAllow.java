package org.acme.features.market.color.application.allow;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.security.Allow;
import org.acme.features.market.color.domain.model.ColorRef;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ColorUpdateAllow {

  /**
   * @autogenerated EntityGenerator
   * @param reference
   * @param allowed
   * @return
   */
  public static ColorUpdateAllow build(final Optional<ColorRef> reference, final boolean allowed) {
    return build(reference, allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param reference
   * @param allowed
   * @param reason
   * @return
   */
  public static ColorUpdateAllow build(final Optional<ColorRef> reference, final boolean allowed,
      final String reason) {
    return ColorUpdateAllow.builder().reference(reference.orElse(null)).detail(CompletableFuture
        .completedFuture(Allow.builder().allowed(allowed).description(reason).build())).build();
  }

  /**
   * @autogenerated EntityGenerator
   */
  private final ColorRef reference;

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private CompletionStage<Allow> detail;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<ColorRef> getReference() {
    return Optional.ofNullable(reference);
  }
}
