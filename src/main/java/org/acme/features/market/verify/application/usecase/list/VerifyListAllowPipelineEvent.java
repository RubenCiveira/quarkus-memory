package org.acme.features.market.verify.application.usecase.list;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowPipelineEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class VerifyListAllowPipelineEvent extends AllowPipelineEvent {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @return
   */
  public static VerifyListAllowPipelineEvent build(final Interaction query, final boolean allowed) {
    return build(query, allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @param reason
   * @return
   */
  public static VerifyListAllowPipelineEvent build(final Interaction query, final boolean allowed,
      final String reason) {
    return VerifyListAllowPipelineEvent.builder().query(query)
        .detail(Allow.builder().allowed(allowed).description(reason).build()).build();
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
    return "verify";
  }
}
