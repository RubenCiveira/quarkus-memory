package org.acme.common.security;

import java.util.function.Function;
import org.acme.common.action.Interaction;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public abstract class AllowPipelineStageEvent {

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private Allow detail;

  @NonNull
  public final Interaction query;

  public abstract String resourceName();

  public abstract String actionName();

  public void map(Function<Allow, Allow> allowed) {
    detail = allowed.apply(detail);
  }
}
