package org.acme.features.market.medal.application.usecase.create;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowPipelineEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MedalCreateAllowPipelineEvent extends AllowPipelineEvent {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @return
   */
  public static MedalCreateAllowPipelineEvent build(final Interaction query,
      final boolean allowed) {
    return build(query, allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @param reason
   * @return
   */
  public static MedalCreateAllowPipelineEvent build(final Interaction query, final boolean allowed,
      final String reason) {
    return MedalCreateAllowPipelineEvent.builder().query(query)
        .detail(Allow.builder().allowed(allowed).description(reason).build()).build();
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String actionName() {
    return "create";
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String resourceName() {
    return "medal";
  }
}
