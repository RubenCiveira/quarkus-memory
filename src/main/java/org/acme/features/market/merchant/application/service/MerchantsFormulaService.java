package org.acme.features.market.merchant.application.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.application.service.event.MerchantFormulaBuilderPipelineStageEvent;
import org.acme.features.market.merchant.domain.model.Merchant;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MerchantsFormulaService {

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated FormulaServiceGenerator
   */
  private final Event<MerchantFormulaBuilderPipelineStageEvent> formulaBuilder;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated FormulaServiceGenerator
   * @param prev The source interaction
   * @param original The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<MerchantDto> copyWithFormulas(Interaction prev, Merchant original,
      MerchantDto source) {
    MerchantFormulaBuilderPipelineStageEvent formulas = MerchantFormulaBuilderPipelineStageEvent
        .builder().dto(CompletableFuture.completedFuture(source)).original(original)
        .interaction(prev).build();
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
  public CompletionStage<MerchantDto> copyWithFormulas(Interaction prev, MerchantDto source) {
    MerchantFormulaBuilderPipelineStageEvent formulas = MerchantFormulaBuilderPipelineStageEvent
        .builder().dto(CompletableFuture.completedFuture(source)).interaction(prev).build();
    formulaBuilder.fire(formulas);
    return formulas.getDto();
  }
}