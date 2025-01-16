package org.acme.features.market.verify.application.service.event;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

import org.acme.common.action.Interaction;
import org.acme.features.market.verify.application.VerifyDto;
import org.acme.features.market.verify.domain.model.Verify;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VerifyFormulaBuilderPipelineStageEvent {

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   */
  Verify original;

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   */
  @NonNull
  private CompletionStage<VerifyDto> dto;

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   * @return
   */
  public Optional<Verify> getOriginal() {
    return Optional.ofNullable(original);
  }

  /**
   * @autogenerated FormulaBuilderPipelineStageEventGenerator
   * @param mapper
   */
  public void tap(Consumer<VerifyDto> mapper) {
    dto = dto.thenApply(dto -> {
      mapper.accept(dto);
      return dto;
    });
  }
}