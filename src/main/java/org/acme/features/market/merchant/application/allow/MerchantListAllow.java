package org.acme.features.market.merchant.application.allow;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.security.Allow;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantListAllow {

  /**
   * @autogenerated EntityGenerator
   * @param allowed
   * @return
   */
  public static MerchantListAllow build(final boolean allowed) {
    return build(allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param allowed
   * @param reason
   * @return
   */
  public static MerchantListAllow build(final boolean allowed, final String reason) {
    return MerchantListAllow.builder().detail(CompletableFuture
        .completedFuture(Allow.builder().allowed(allowed).description(reason).build())).build();
  }

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private CompletionStage<Allow> detail;
}
