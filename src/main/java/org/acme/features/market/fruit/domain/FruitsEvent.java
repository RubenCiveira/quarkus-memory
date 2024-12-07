package org.acme.features.market.fruit.domain;

import org.acme.features.market.fruit.domain.interaction.command.FruitCreateCommand;
import org.acme.features.market.fruit.domain.interaction.result.FruitCreateResult;
import org.acme.features.market.fruit.domain.interaction.result.FruitListResult;
import org.acme.features.market.fruit.domain.interaction.result.FruitRetrieveResult;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitsEvent {

    /**
     * Event bus for individual result events
     *
     * @autogenerated CreateTraitGenerator
     */
    private final Event<FruitCreateResult> createEventBus;

    /**
     * Event bus for individual result events
     *
     * @autogenerated CreateTraitGenerator
     */
    private final Event<FruitCreateResult> createdEventBus;

    /**
     * Event bus for list result events
     *
     * @autogenerated ListTraitGenerator
     */
    private final Event<FruitListResult> listResultEventBus;

    /**
     * Event bus for individual result events
     *
     * @autogenerated RetrieveTraitGenerator
     */
    private final Event<FruitRetrieveResult> resultEventBus;

    /**
     * The param modified after
     *
     * @autogenerated CreateTraitGenerator
     * @param event The result event
     * @return The param modified after
     */
    public FruitCreateResult fireCreateCommand(final FruitCreateCommand event) {
        createdEventBus.fire(event);
        return event;
    }

    /**
     * The param modified after
     *
     * @autogenerated CreateTraitGenerator
     * @param event The result event
     * @return The param modified after
     */
    public FruitCreateResult fireCreateResult(final FruitCreateResult event) {
        createEventBus.fire(event);
        return event;
    }

    /**
     * The param modified after
     *
     * @autogenerated ListTraitGenerator
     * @param event The result event
     * @return The param modified after
     */
    public FruitListResult fireListResult(final FruitListResult event) {
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
    public FruitRetrieveResult fireRetrieveResult(final FruitRetrieveResult event) {
        resultEventBus.fire(event);
        return event;
    }
}
