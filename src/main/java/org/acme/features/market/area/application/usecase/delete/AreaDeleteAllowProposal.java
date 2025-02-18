package org.acme.features.market.area.application.usecase.delete;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowProposal;
import org.acme.features.market.area.domain.model.AreaRef;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AreaDeleteAllowProposal extends AllowProposal {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param reference
   * @param allowed
   * @return
   */
  public static AreaDeleteAllowProposal build(final Interaction query,
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
  public static AreaDeleteAllowProposal build(final Interaction query,
      final Optional<AreaRef> reference, final boolean allowed, final String reason) {
    return AreaDeleteAllowProposal.builder().query(query).reference(reference.orElse(null))
        .detail(Allow.builder().allowed(allowed).description(reason).build()).build();
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param allow
   * @return
   */
  public static Allow resolveWith(final Event<AreaDeleteAllowProposal> emitter,
      final AreaDeleteAllowProposal allow) {
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
  public static Allow resolveWith(final Event<AreaDeleteAllowProposal> emitter,
      final Interaction query, final Optional<AreaRef> reference, final boolean allowed) {
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
  public static Allow resolveWith(final Event<AreaDeleteAllowProposal> emitter,
      final Interaction query, final Optional<AreaRef> reference, final boolean allowed,
      final String reason) {
    return resolveWith(emitter, build(query, reference, allowed, reason));
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
    return "delete";
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
