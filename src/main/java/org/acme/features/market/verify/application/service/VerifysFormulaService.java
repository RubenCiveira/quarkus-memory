package org.acme.features.market.verify.application.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.verify.application.VerifyDto;
import org.acme.features.market.verify.application.service.event.VerifyFormulaBuilder;
import org.acme.features.market.verify.domain.model.Verify;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class VerifysFormulaService {

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated FormulaServiceGenerator
   */
  private final Event<VerifyFormulaBuilder> formulaBuilder;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated FormulaServiceGenerator
   * @param prev The source interaction
   * @param original The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<VerifyDto> copyWithFormulas(Interaction prev, Verify original,
      VerifyDto source) {
    VerifyFormulaBuilder formulas = VerifyFormulaBuilder.builder()
        .dto(CompletableFuture.completedFuture(source)).original(original).build(prev);
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
  public CompletionStage<VerifyDto> copyWithFormulas(Interaction prev, VerifyDto source) {
    VerifyFormulaBuilder formulas =
        VerifyFormulaBuilder.builder().dto(CompletableFuture.completedFuture(source)).build(prev);
    formulaBuilder.fire(formulas);
    return formulas.getDto();
  }
}
