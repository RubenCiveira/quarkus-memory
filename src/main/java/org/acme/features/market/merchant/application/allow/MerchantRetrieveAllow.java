package org.acme.features.market.merchant.application.allow;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.security.Allow;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantRetrieveAllow {

  /**
   * @autogenerated EntityGenerator
   * @param reference
   * @param allowed
   * @return
   */
  public static MerchantRetrieveAllow build(final Optional<MerchantRef> reference,
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
  public static MerchantRetrieveAllow build(final Optional<MerchantRef> reference,
      final boolean allowed, final String reason) {
    return MerchantRetrieveAllow.builder().reference(reference.orElse(null))
        .detail(CompletableFuture
            .completedFuture(Allow.builder().allowed(allowed).description(reason).build()))
        .build();
  }

  /**
   * @autogenerated EntityGenerator
   */
  private final MerchantRef reference;

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private CompletionStage<Allow> detail;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<MerchantRef> getReference() {
    return Optional.ofNullable(reference);
  }
}
