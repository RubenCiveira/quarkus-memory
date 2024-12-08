package org.acme.features.market.fruit.infrastructure.driver;

import java.time.Duration;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.market.fruit.application.dto.FruitsUsecase;
import org.acme.features.market.fruit.domain.interaction.FruitCursor;
import org.acme.features.market.fruit.domain.interaction.FruitDto;
import org.acme.features.market.fruit.domain.interaction.FruitFilter;
import org.acme.features.market.fruit.domain.interaction.command.FruitCreateCommand;
import org.acme.features.market.fruit.domain.interaction.query.FruitListQuery;
import org.acme.features.market.fruit.domain.interaction.query.FruitRetrieveQuery;
import org.acme.openapi.api.FruitApi;
import org.acme.openapi.model.Fruit;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class ApiFirstFruitController implements FruitApi {
  private final FruitsUsecase fruits;

  public ApiFirstFruitController(FruitsUsecase fruits) {
    this.fruits = fruits;
  }

  @Override
  public Response fruitApiCreate(Fruit fruit) {
    Actor actor = new Actor();
    Connection connection = new Connection();
    FruitDto dto = FruitDto.builder().uid(fruit.getUid()).name(fruit.getName())
        .version(fruit.getVersion()).build();
    FruitCreateCommand command =
        FruitCreateCommand.builder().actor(actor).connection(connection).dto(dto).build();
    return fruits.create(command).map(item -> item.getFruit().map(res -> Response.ok(res).build())
        .orElseGet(() -> Response.status(404).build())).await().atMost(Duration.ofSeconds(2));
  }

  @Override
  public Response fruitApiDelete(String uid) {
    return null;
  }

  @Override
  public Response fruitApiList(Integer limit) {
    FruitFilter.FruitFilterBuilder filter = FruitFilter.builder();
    FruitCursor.FruitCursorBuilder cursor = FruitCursor.builder();
    cursor = cursor.limit(limit);// == limit ? 10 : limit);
    Actor actor = new Actor();
    Connection connection = new Connection();
    return fruits
        .list(FruitListQuery.builder().actor(actor).connection(connection).filter(filter.build())
            .cursor(cursor.build()).build())
        .map(items -> Response.ok(items.getFruits()).build()).await().atMost(Duration.ofSeconds(2));
  }

  @Override
  public Response fruitApiRetrieve(String uid) {
    Actor actor = new Actor();
    Connection connection = new Connection();
    return fruits
        .retrieve(FruitRetrieveQuery.builder().actor(actor).connection(connection).uid(uid).build())
        .map(item -> item.getFruit().map(fruit -> Response.ok(fruit).build())
            .orElseGet(() -> Response.status(404).build()))
        .await().atMost(Duration.ofSeconds(2));
  }

  @Override
  public Response fruitApiUpdate(String uid, Fruit fruit) {
    return null;
  }

}
