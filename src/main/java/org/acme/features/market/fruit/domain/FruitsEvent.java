package org.acme.features.market.fruit.domain;

import org.acme.features.market.fruit.domain.interaction.result.FruitListResult;
import org.acme.features.market.fruit.domain.interaction.result.FruitRetrieveResult;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitsEvent {

  /**
   * Event bus for list result events
   *
   * @autogenerated ListTraitGenerator
   */
  private final Event<FruitListResult> listResultEventBus;

  /**
   * Event bus for individual result events
   *
   * @autogenerated ListTraitGenerator
   */
  private final Event<FruitRetrieveResult> resultEventBus;

  /**
   * The param modified after
   *
   * @autogenerated ListTraitGenerator
   * @param event The result event
   * @return The param modified after
   */
  public FruitListResult fireFruitListResult(final FruitListResult event) {
    listResultEventBus.fire(event);
    return event;
  }

  /**
   * The param modified after
   *
   * @autogenerated RetrieveTraitGenerator
   * @param event The result event
   * @return The param modified after
   */
  public FruitRetrieveResult fireFruitRetrieveResult(final FruitRetrieveResult event) {
    resultEventBus.fire(event);
    return event;
  }
}
