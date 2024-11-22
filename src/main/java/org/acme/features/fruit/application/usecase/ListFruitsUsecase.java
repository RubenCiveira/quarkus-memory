package org.acme.features.fruit.application.usecase;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.features.fruit.application.dto.FruitListResultDto;
import org.acme.features.fruit.application.dto.FruitReadResultDto;
import org.acme.features.fruit.application.usecase.event.FruitListAllow;
import org.acme.features.fruit.domain.Fruits;
import org.acme.features.fruit.domain.interaction.FruitSlice;
import org.acme.features.fruit.domain.interaction.query.FruitList;
import org.acme.features.fruit.domain.interaction.result.FruitListResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;

@RequestScoped
public class ListFruitsUsecase {
  private final Fruits fruits;
  private final Event<Allow> allowEventBus;
  private final Event<FruitListResultDto> listEventBus;
  private final Event<FruitReadResultDto> readEventBus;

  public ListFruitsUsecase(Fruits fruits, @FruitListAllow Event<Allow> allowEventBus,
      Event<FruitListResultDto> listEventBus, Event<FruitReadResultDto> readEventBus) {
    super();
    this.fruits = fruits;
    this.allowEventBus = allowEventBus;
    this.listEventBus = listEventBus;
    this.readEventBus = readEventBus;
  }

  public Uni<Allow> allow(Interaction request) {
    Allow initial =
        Allow.builder().allowed(true).name("list").description("Read fruits is enabled by default")
            .actor(request.getActor()).connection(request.getConnection()).build();
    allowEventBus.fire(initial);
    return Uni.createFrom().item(initial);
  }

  public Uni<FruitListResultDto> fruits(FruitList query) {
    return allow(query).flatMap(allowed -> allowed.isAllowed()
        ? fruits.fruits(query).map(list -> this.notify(list, query.getCursor().getLimit()))
        : Uni.createFrom().failure(() -> new RuntimeException("Unauthorized")));
  }

  private FruitListResultDto notify(FruitListResult dto, Integer limit) {
    FruitListResultDto result = FruitListResultDto.from(dto);
    boolean more = result.size() == limit;
    listEventBus.fire(result);
    if( null != limit && more ) {
      while( result.size() < limit) {
        FruitSlice other = dto.next();
        FruitListResultDto next = FruitListResultDto.from( FruitListResult.from(dto, other));
        listEventBus.fire(next);
        result.append( next );
      }
    }
    result.getFruits().forEach(fruit -> readEventBus.fire(FruitReadResultDto.from(dto, fruit)));
    // Tengo que volver a pedir los nuevos
    return result;
  }
  
  private void untilNext(FruitListResultDto result, Integer limit) {
    
  }
}
