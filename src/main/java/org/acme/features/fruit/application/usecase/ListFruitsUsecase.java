package org.acme.features.fruit.application.usecase;

import org.acme.common.action.Interaction;
import org.acme.common.security.Allow;
import org.acme.features.fruit.application.dto.FruitListResultDto;
import org.acme.features.fruit.application.dto.FruitReadResultDto;
import org.acme.features.fruit.application.usecase.event.FruitListAllow;
import org.acme.features.fruit.domain.Fruits;
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
        ? fruits.fruits(query).flatMap(list -> this.notify(list, query.getCursor().getLimit()))
        : Uni.createFrom().failure(() -> new RuntimeException("Unauthorized")));
  }

  private Uni<FruitListResultDto> notify(FruitListResult dto, Integer limit) {
    FruitListResultDto initialResult = FruitListResultDto.from(dto);
    int readed = initialResult.size();
    boolean more = readed == limit;
    listEventBus.fire(initialResult);
    return Uni.createFrom().item(initialResult).flatMap(result -> {
      if (limit != null && more) {

        return fetchMorePages(dto, result, limit, windowSize(initialResult, readed, limit));
      } else {
        return Uni.createFrom().item(result);
      }
    }).invoke(result -> {
      result.getFruits().forEach(fruit -> readEventBus.fire(FruitReadResultDto.from(dto, fruit)));
    });
  }
  
  private int windowSize(FruitListResultDto initialResult, int readed, Integer limit) {
    int neededResults = (limit - initialResult.size());
    float aceptationRange = ((float)readed) / ((float)initialResult.size() );
    // if we remove a lot of items, we ask for more items to the gateway to ensure the results
    return Math.max(limit, (int) (neededResults * (1 + aceptationRange)));
  }

  private Uni<FruitListResultDto> fetchMorePages(FruitListResult current, FruitListResultDto result,
      Integer limit, Integer window) {
    return current.next(window).flatMap(slice -> {
      FruitListResult nextResult = FruitListResult.from(result, slice);
      FruitListResultDto next = FruitListResultDto.from(nextResult);
      int readed = next.size();
      listEventBus.fire(next);
      result.append(next, limit-result.size());
      boolean complete = readed == 0 || result.size() >= limit;
      return complete ? Uni.createFrom().item(result)
          : fetchMorePages(nextResult, result, limit, windowSize(next, readed, limit) );
    });
  }
}
