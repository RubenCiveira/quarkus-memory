package org.acme.features.fruit.application.usecase;

import org.acme.common.action.Interaction;
import org.acme.common.action.Slide;
import org.acme.features.fruit.application.dto.FruitListResultDto;
import org.acme.features.fruit.application.dto.FruitReadResultDto;
import org.acme.features.fruit.domain.Fruits;
import org.acme.features.fruit.domain.interaction.query.FruitList;
import org.acme.features.fruit.domain.model.Fruit;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ListFruitsUsecase {
  private final Fruits service;
  private final Event<ListAllow> allowEventBus;
  private final Event<FruitListResultDto> listEventBus;
  private final Event<FruitReadResultDto> readEventBus;

  public Uni<ListAllow> allow(Interaction request) {
    ListAllow initial = ListAllow.from(request);
    allowEventBus.fire(initial);
    return Uni.createFrom().item(initial);
  }

  public Uni<FruitListResultDto> fruits(FruitList query) {
    return allow(query).flatMap(allowed -> allowed.isAllowed()
        ? service.list(query.getFilter(), query.getCursor())
            .flatMap(slide -> this.map(slide, query))
        : Uni.createFrom().failure(() -> new RuntimeException("Unauthorized")));
  }

  private Uni<FruitListResultDto> map(Slide<Fruit> slide, FruitList query) {
    // fill the slide from the listEventBus remove.
    return slide.filterAndFill(query.getCursor().getLimit(), fruits -> {
      FruitListResultDto dto = FruitListResultDto.from(query, fruits);
      listEventBus.fire(dto);
      return dto.getFruits();
    }).map(list -> FruitListResultDto.fromDto(query, list)).invoke(result -> result.getFruits()
        .forEach(fruit -> readEventBus.fire(FruitReadResultDto.from(query, fruit))));
  }
}
