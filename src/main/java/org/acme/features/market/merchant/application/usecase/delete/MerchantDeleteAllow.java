package org.acme.features.market.merchant.application.usecase.delete;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowResult;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantDeleteAllow extends AllowResult {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param reference
   * @param allowed
   * @return
   */
  public static MerchantDeleteAllow build(final Interaction query,
      final Optional<MerchantRef> reference, final boolean allowed) {
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
  public static MerchantDeleteAllow build(final Interaction query,
      final Optional<MerchantRef> reference, final boolean allowed, final String reason) {
    return MerchantDeleteAllow.builder().query(query).reference(reference.orElse(null))
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
   * @return
   */
  public String actionName() {
    return "delete";
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<MerchantRef> getReference() {
    return Optional.ofNullable(reference);
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String resourceName() {
    return "merchant";
  }
}
