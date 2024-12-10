package org.acme.features.market.fruit.infrastructure.driver;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.market.fruit.application.interaction.FruitDto;
import org.acme.features.market.fruit.application.interaction.query.FruitListQuery;
import org.acme.features.market.fruit.application.interaction.result.FruitListResult;
import org.acme.features.market.fruit.application.usecase.ListFruitUsecase;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.openapi.api.FruitApi;
import org.acme.openapi.model.Fruit;
import org.acme.openapi.model.FruitApiList200Response;
import org.acme.openapi.model.FruitApiList200ResponseNext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class ApiFirstFruitController implements FruitApi {
  private final ListFruitUsecase fruits;

  public ApiFirstFruitController(ListFruitUsecase fruits) {
    this.fruits = fruits;
  }

  @Override
  public Response fruitApiCreate(Fruit fruit) {
    return null;
    // Actor actor = new Actor();
    // Connection connection = new Connection();
    // FruitDto dto = FruitDto.builder().uid(fruit.getUid()).name(fruit.getName())
    // .version(fruit.getVersion()).build();
    // FruitCreateCommand command =
    // FruitCreateCommand.builder().actor(actor).connection(connection).dto(dto).build();
    // return fruits.create(command).map(item -> item.getFruit().map(res ->
    // Response.ok(res).build())
    // .orElseGet(() -> Response.status(404).build())).await().atMost(Duration.ofSeconds(2));
  }

  @Override
  public Response fruitApiDelete(String uid) {
    return null;
  }

  @Override
  public Response fruitApiList(Integer limit, String sinceUid) {
    FruitFilter.FruitFilterBuilder filter = FruitFilter.builder();
    FruitCursor.FruitCursorBuilder cursor = FruitCursor.builder();
    cursor = cursor.limit(limit);// == limit ? 10 : limit);
    cursor.sinceUid( sinceUid );
    Actor actor = new Actor();
    Connection connection = new Connection();
    FruitListResult list = fruits.list(FruitListQuery.builder().actor(actor).connection(connection)
        .filter(filter.build()).cursor(cursor.build()).build());
    // .map(items -> Response.ok(items.getFruits()).build()).await().atMost(Duration.ofSeconds(2));
    try {
      FruitApiList200Response res = new FruitApiList200Response();
      List<FruitDto> fruits = list.getFruits().get(1, TimeUnit.SECONDS);
      res.setContent( toApiModel(fruits) );
      res.setNext( next(fruits) );
      return Response.ok(res).build();
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  @Override
  public Response fruitApiRetrieve(String uid) {
    return null;
    // Actor actor = new Actor();
    // Connection connection = new Connection();
    // return fruits
    // .retrieve(FruitRetrieveQuery.builder().actor(actor).connection(connection).uid(uid).build())
    // .map(item -> item.getFruit().map(fruit -> Response.ok(fruit).build())
    // .orElseGet(() -> Response.status(404).build()))
    // .await().atMost(Duration.ofSeconds(2));
  }

  @Override
  public Response fruitApiUpdate(String uid, Fruit fruit) {
    return null;
  }

  public FruitApiList200ResponseNext next(List<FruitDto> list) {
    if( list.isEmpty() ) {
      return null;
    } else {
      FruitApiList200ResponseNext next = new FruitApiList200ResponseNext();
      FruitDto last = list.get(list.size()-1);
      next.setSinceUid( last.getUid() );
      return next;
    }
  }
  
  public List<Fruit> toApiModel(List<FruitDto> fruit) {
    return fruit.stream().map(this::toApiModel).toList();
  }

  public Fruit toApiModel(FruitDto dto) {
    Fruit fruit = new Fruit();
    fruit.setName( dto.getName() );
    fruit.setUid( dto.getUid() );
    fruit.setVersion( dto.getVersion() );
    return fruit;
  }
}
