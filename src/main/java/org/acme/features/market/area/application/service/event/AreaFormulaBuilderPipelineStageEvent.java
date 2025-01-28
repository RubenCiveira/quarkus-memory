package org.acme.features.market.area.application.service.event;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

import org.acme.common.action.Interaction;
import org.acme.features.market.area.application.AreaDto;
import org.acme.features.market.area.domain.model.Area;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AreaFormulaBuilderPipelineStageEvent {

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   */
  @NonNull
  private CompletionStage<AreaDto> dto;

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   */
  private Area original;

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   * @return
   */
  public Optional<Area> getOriginal() {
    return Optional.ofNullable(original);
  }

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   * @param mapper
   */
  public void tap(Consumer<AreaDto> mapper) {
    dto = dto.thenApply(dto -> {
      mapper.accept(dto);
      return dto;
    });
  }
}
