package org.acme.features.market.medal.application.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.service.event.MedalFormulaBuilderPipelineStageEvent;
import org.acme.features.market.medal.domain.model.Medal;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MedalsFormulaService {

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated FormulaServiceGenerator
   */
  private final Event<MedalFormulaBuilderPipelineStageEvent> formulaBuilder;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated FormulaServiceGenerator
   * @param prev The source interaction
   * @param original The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<MedalDto> copyWithFormulas(Interaction prev, Medal original,
      MedalDto source) {
    MedalFormulaBuilderPipelineStageEvent formulas = MedalFormulaBuilderPipelineStageEvent.builder()
        .dto(CompletableFuture.completedFuture(source)).original(original).interaction(prev)
        .build();
    formulaBuilder.fire(formulas);
    return formulas.getDto();
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated FormulaServiceGenerator
   * @param prev The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<MedalDto> copyWithFormulas(Interaction prev, MedalDto source) {
    MedalFormulaBuilderPipelineStageEvent formulas = MedalFormulaBuilderPipelineStageEvent.builder()
        .dto(CompletableFuture.completedFuture(source)).interaction(prev).build();
    formulaBuilder.fire(formulas);
    return formulas.getDto();
  }
}