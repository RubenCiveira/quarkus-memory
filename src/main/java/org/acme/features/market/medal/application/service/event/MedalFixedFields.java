package org.acme.features.market.medal.application.service.event;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.medal.domain.model.MedalRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MedalFixedFields extends Interaction {

  /**
   * @autogenerated FixedFieldsGenerator
   */
  private final MedalRef medal;

  /**
   * The list of fixed fields
   *
   * @autogenerated FixedFieldsGenerator
   */
  @NonNull
  private CompletionStage<Set<String>> fixed;

  /**
   * @autogenerated FixedFieldsGenerator
   * @param fields
   */
  public void add(String... fields) {
    fixed = fixed.thenApply(set -> {
      for (String field : fields) {
        set.add(field);
      }
      return set;
    });
  }

  /**
   * @autogenerated FixedFieldsGenerator
   * @return
   */
  public Optional<MedalRef> getMedal() {
    return Optional.ofNullable(medal);
  }
}
