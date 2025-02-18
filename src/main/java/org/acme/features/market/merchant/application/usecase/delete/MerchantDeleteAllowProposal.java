package org.acme.features.market.merchant.application.usecase.delete;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowProposal;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantDeleteAllowProposal extends AllowProposal {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param reference
   * @param allowed
   * @return
   */
  public static MerchantDeleteAllowProposal build(final Interaction query,
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
  public static MerchantDeleteAllowProposal build(final Interaction query,
      final Optional<MerchantRef> reference, final boolean allowed, final String reason) {
    return MerchantDeleteAllowProposal.builder().query(query).reference(reference.orElse(null))
        .detail(Allow.builder().allowed(allowed).description(reason).build()).build();
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param allow
   * @return
   */
  public static Allow resolveWith(final Event<MerchantDeleteAllowProposal> emitter,
      final MerchantDeleteAllowProposal allow) {
    emitter.fire(allow);
    return allow.getDetail();
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param query
   * @param reference
   * @param allowed
   * @return
   */
  public static Allow resolveWith(final Event<MerchantDeleteAllowProposal> emitter,
      final Interaction query, final Optional<MerchantRef> reference, final boolean allowed) {
    return resolveWith(emitter, build(query, reference, allowed));
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param query
   * @param reference
   * @param allowed
   * @param reason
   * @return
   */
  public static Allow resolveWith(final Event<MerchantDeleteAllowProposal> emitter,
      final Interaction query, final Optional<MerchantRef> reference, final boolean allowed,
      final String reason) {
    return resolveWith(emitter, build(query, reference, allowed, reason));
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
