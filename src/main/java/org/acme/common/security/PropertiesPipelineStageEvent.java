package org.acme.common.security;

import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

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
  private CompletionStage<Set<String>> fields;

  @NonNull
  public final Interaction query;

  public abstract String resourceName();

  public abstract String viewName();

  public void map(Function<Set<String>, Set<String>> allowed) {
    fields = fields.thenApply(allowed::apply);
  }

  public void mergeMap(Function<Set<String>, CompletionStage<Set<String>>> allowed) {
    fields = fields.thenCompose(allowed::apply);
  }

  public void add(String... newfields) {
    fields = fields.thenApply(set -> {
      for (String field : newfields) {
        set.add(field);
      }
      return set;
    });
  }
}
