package org.acme.features.market.medal.application.service.event;

import java.util.Optional;
import java.util.function.Consumer;

import org.acme.common.action.Interaction;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.domain.model.Medal;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedalFormulaBuilderPipelineEvent {

  /**
   * @autogenerated FormulaBuilderPipelineEventGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated FormulaBuilderPipelineEventGenerator
   */
  @NonNull
  private MedalDto dto;

  /**
   * @autogenerated FormulaBuilderPipelineEventGenerator
   */
  private Medal original;

  /**
   * @autogenerated FormulaBuilderPipelineEventGenerator
   * @return
   */
  public Optional<Medal> getOriginal() {
    return Optional.ofNullable(original);
  }

  /**
   * @autogenerated FormulaBuilderPipelineEventGenerator
   * @param mapper
   */
  public void peek(Consumer<MedalDto> mapper) {
    mapper.accept(dto);
  }
}
