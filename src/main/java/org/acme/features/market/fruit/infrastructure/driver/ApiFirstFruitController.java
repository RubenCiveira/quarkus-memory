package org.acme.features.market.fruit.infrastructure.driver;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.market.fruit.application.interaction.FruitDto;
import org.acme.features.market.fruit.application.interaction.command.FruitCreateCommand;
import org.acme.features.market.fruit.application.interaction.query.FruitListQuery;
import org.acme.features.market.fruit.application.interaction.query.FruitRetrieveQuery;
import org.acme.features.market.fruit.application.interaction.result.FruitCreateResult;
import org.acme.features.market.fruit.application.interaction.result.FruitListResult;
import org.acme.features.market.fruit.application.interaction.result.FruitRetrieveResult;
import org.acme.features.market.fruit.application.usecase.CreateFruitUsecase;
import org.acme.features.market.fruit.application.usecase.ListFruitUsecase;
import org.acme.features.market.fruit.application.usecase.RetrieveFruitUsecase;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.openapi.api.FruitApi;
import org.acme.openapi.model.Fruit;
import org.acme.openapi.model.FruitApiList200Response;
import org.acme.openapi.model.FruitApiList200ResponseNext;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ApiFirstFruitController implements FruitApi {
  private final ListFruitUsecase list;
  private final RetrieveFruitUsecase retrieve;
  private final CreateFruitUsecase create;

  @Override
  public Response fruitApiCreate(Fruit fruit) {

    Actor actor = new Actor();
    Connection connection = new Connection();
    FruitDto dto = FruitDto.builder().uid(fruit.getUid()).name(fruit.getName())
        .version(fruit.getVersion()).build();
    FruitCreateCommand command =
        FruitCreateCommand.builder().actor(actor).connection(connection).dto(dto).build();
    FruitCreateResult result = create.create(command);
    try {
      Optional<FruitDto> output = result.getFruit().get(1, TimeUnit.SECONDS);
      return output.map(res -> Response.ok(toApiModel(res)).build())
          .orElseGet(() -> Response.status(404).build());
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
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
    cursor.sinceUid(sinceUid);
    Actor actor = new Actor();
    Connection connection = new Connection();
    FruitListResult result = list.list(FruitListQuery.builder().actor(actor).connection(connection)
        .filter(filter.build()).cursor(cursor.build()).build());
    // .map(items -> Response.ok(items.getFruits()).build()).await().atMost(Duration.ofSeconds(2));
    try {
      List<FruitDto> fruits = result.getFruits().get(1, TimeUnit.SECONDS);
      FruitApiList200Response res = new FruitApiList200Response();
      res.setContent(toApiModel(fruits));
      res.setNext(next(fruits));
      return Response.ok(res).build();
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  @Override
  public Response fruitApiRetrieve(String uid) {
    Actor actor = new Actor();
    Connection connection = new Connection();
    FruitRetrieveResult result = retrieve.retrieve(
        FruitRetrieveQuery.builder().actor(actor).connection(connection).uid(uid).build());
    try {
      Optional<FruitDto> fruit = result.getFruit().get(1, TimeUnit.SECONDS);
      return fruit.map(res -> Response.ok(toApiModel(res)).build())
          .orElseGet(() -> Response.status(404).build());
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  @Override
  public Response fruitApiUpdate(String uid, Fruit fruit) {
    return null;
  }

  public FruitApiList200ResponseNext next(List<FruitDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      FruitApiList200ResponseNext next = new FruitApiList200ResponseNext();
      FruitDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid());
      return next;
    }
  }

  public List<Fruit> toApiModel(List<FruitDto> fruit) {
    return fruit.stream().map(this::toApiModel).toList();
  }

  public Fruit toApiModel(FruitDto dto) {
    Fruit fruit = new Fruit();
    fruit.setName(dto.getName());
    fruit.setUid(dto.getUid());
    fruit.setVersion(dto.getVersion());
    return fruit;
  }
}
