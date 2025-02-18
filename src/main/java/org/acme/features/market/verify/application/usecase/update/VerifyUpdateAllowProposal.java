package org.acme.features.market.verify.application.usecase.update;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowProposal;
import org.acme.features.market.verify.domain.model.VerifyRef;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class VerifyUpdateAllowProposal extends AllowProposal {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param reference
   * @param allowed
   * @return
   */
  public static VerifyUpdateAllowProposal build(final Interaction query,
      final Optional<VerifyRef> reference, final boolean allowed) {
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
  public static VerifyUpdateAllowProposal build(final Interaction query,
      final Optional<VerifyRef> reference, final boolean allowed, final String reason) {
    return VerifyUpdateAllowProposal.builder().query(query).reference(reference.orElse(null))
        .detail(Allow.builder().allowed(allowed).description(reason).build()).build();
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param allow
   * @return
   */
  public static Allow resolveWith(final Event<VerifyUpdateAllowProposal> emitter,
      final VerifyUpdateAllowProposal allow) {
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
  public static Allow resolveWith(final Event<VerifyUpdateAllowProposal> emitter,
      final Interaction query, final Optional<VerifyRef> reference, final boolean allowed) {
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
  public static Allow resolveWith(final Event<VerifyUpdateAllowProposal> emitter,
      final Interaction query, final Optional<VerifyRef> reference, final boolean allowed,
      final String reason) {
    return resolveWith(emitter, build(query, reference, allowed, reason));
  }

  /**
   * @autogenerated EntityGenerator
   */
  private final VerifyRef reference;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String actionName() {
    return "update";
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<VerifyRef> getReference() {
    return Optional.ofNullable(reference);
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String resourceName() {
    return "verify";
  }
}
