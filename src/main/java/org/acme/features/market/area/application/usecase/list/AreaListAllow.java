package org.acme.features.market.area.application.usecase.list;

import java.util.concurrent.CompletableFuture;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.common.security.AllowResult;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AreaListAllow extends AllowResult {

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @return
   */
  public static AreaListAllow build(final Interaction query, final boolean allowed) {
    return build(query, allowed, null);
  }

  /**
   * @autogenerated EntityGenerator
   * @param query
   * @param allowed
   * @param reason
   * @return
   */
  public static AreaListAllow build(final Interaction query, final boolean allowed,
      final String reason) {
    return AreaListAllow.builder().query(query).detail(CompletableFuture
        .completedFuture(Allow.builder().allowed(allowed).description(reason).build())).build();
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
    return "area";
  }
}
