package org.acme.features.market.fruit.application.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.service.event.FruitFormulaBuilderPipelineStageEvent;
import org.acme.features.market.fruit.domain.model.Fruit;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitsFormulaService {

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated FormulaServiceGenerator
   */
  private final Event<FruitFormulaBuilderPipelineStageEvent> formulaBuilder;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated FormulaServiceGenerator
   * @param prev The source interaction
   * @param original The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<FruitDto> copyWithFormulas(Interaction prev, Fruit original,
      FruitDto source) {
    FruitFormulaBuilderPipelineStageEvent formulas = FruitFormulaBuilderPipelineStageEvent.builder()
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
  public CompletionStage<FruitDto> copyWithFormulas(Interaction prev, FruitDto source) {
    FruitFormulaBuilderPipelineStageEvent formulas = FruitFormulaBuilderPipelineStageEvent.builder()
        .dto(CompletableFuture.completedFuture(source)).interaction(prev).build();
    formulaBuilder.fire(formulas);
    return formulas.getDto();
  }
}