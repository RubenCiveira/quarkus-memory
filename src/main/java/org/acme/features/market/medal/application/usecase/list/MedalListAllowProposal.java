package org.acme.features.market.medal.application.usecase.list;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowProposal;

import jakarta.enterprise.event.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MedalListAllowProposal extends AllowProposal {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @return
   */
  public static MedalListAllowProposal build(final Interaction query, final boolean allowed) {
    return build(query, allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @param reason
   * @return
   */
  public static MedalListAllowProposal build(final Interaction query, final boolean allowed,
      final String reason) {
    return MedalListAllowProposal.builder().query(query)
        .detail(Allow.builder().allowed(allowed).description(reason).build()).build();
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param allow
   * @return
   */
  public static Allow resolveWith(final Event<MedalListAllowProposal> emitter,
      final MedalListAllowProposal allow) {
    emitter.fire(allow);
    return allow.getDetail();
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param query
   * @param allowed
   * @return
   */
  public static Allow resolveWith(final Event<MedalListAllowProposal> emitter,
      final Interaction query, final boolean allowed) {
    return resolveWith(emitter, MedalListAllowProposal.build(query, allowed));
  }

  /**
   * @autogenerated EntityGenerator
   * @param emitter
   * @param query
   * @param allowed
   * @param reason
   * @return
   */
  public static Allow resolveWith(final Event<MedalListAllowProposal> emitter,
      final Interaction query, final boolean allowed, final String reason) {
    return resolveWith(emitter, build(query, allowed, reason));
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String actionName() {
    return "list";
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String resourceName() {
    return "medal";
  }
}
