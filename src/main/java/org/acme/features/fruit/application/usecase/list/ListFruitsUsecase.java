package org.acme.features.fruit.application.usecase.list;

import org.acme.common.security.Actor;
import org.acme.common.security.Allow;
import org.acme.common.security.Connection;
import org.acme.features.fruit.domain.Fruits;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;

@RequestScoped
public class ListFruitsUsecase {
  private final Fruits fruits;
  private final Event<Allow> eventBus;

  public ListFruitsUsecase(Fruits fruits, @FruitsListAllow Event<Allow> eventBus) {
    super();
    this.fruits = fruits;
    this.eventBus = eventBus;
  }

  public Uni<Allow> allow(Actor actor, Connection conn) {
    Allow initial = Allow.builder().allowed(true).name("list")
        .description("Read fruits is enabled by default").actor(actor).connection(conn).build();
    eventBus.fire(initial);
    System.out.println(initial);
    return Uni.createFrom().item(initial);
  }

  public Uni<ListFruitsResult> fruits(ListFruitsQuery query) {
    return allow(query.getActor(), query.getConnection()).flatMap(allowed -> {
      if (allowed.isAllowed()) {
        return fruits.fruits().map(ListFruitsResult::from);
      } else {
        return Uni.createFrom().failure(() -> new RuntimeException("Unexpected value: "));
      }
    });
  }
}
