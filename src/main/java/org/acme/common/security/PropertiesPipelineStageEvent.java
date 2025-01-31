package org.acme.common.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.acme.common.action.Interaction;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public abstract class PropertiesPipelineStageEvent {

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private Set<String> fields;

  @NonNull
  public final Interaction query;

  public abstract String resourceName();

  public abstract String viewName();

  public void add(String... newfields) {
    add(Arrays.asList(newfields));
  }

  public void add(List<String> newfields) {
    try {
      fields.addAll(newfields);
    } catch(UnsupportedOperationException ex) {
      fields = new HashSet<>( newfields );
    }
  }

}
