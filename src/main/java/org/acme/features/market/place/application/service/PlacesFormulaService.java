package org.acme.features.market.place.application.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.place.application.PlaceDto;
import org.acme.features.market.place.application.service.event.PlaceFormulaBuilderPipelineStageEvent;
import org.acme.features.market.place.domain.model.Place;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class PlacesFormulaService {

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated FormulaServiceGenerator
   */
  private final Event<PlaceFormulaBuilderPipelineStageEvent> formulaBuilder;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated FormulaServiceGenerator
   * @param prev The source interaction
   * @param original The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<PlaceDto> copyWithFormulas(Interaction prev, Place original,
      PlaceDto source) {
    PlaceFormulaBuilderPipelineStageEvent formulas = PlaceFormulaBuilderPipelineStageEvent.builder()
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
  public CompletionStage<PlaceDto> copyWithFormulas(Interaction prev, PlaceDto source) {
    PlaceFormulaBuilderPipelineStageEvent formulas = PlaceFormulaBuilderPipelineStageEvent.builder()
        .dto(CompletableFuture.completedFuture(source)).interaction(prev).build();
    formulaBuilder.fire(formulas);
    return formulas.getDto();
  }
}
