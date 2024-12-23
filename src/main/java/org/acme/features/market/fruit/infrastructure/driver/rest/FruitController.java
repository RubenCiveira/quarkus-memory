package org.acme.features.market.fruit.infrastructure.driver.rest;

import java.util.List;

import org.acme.common.rest.CurrentRequest;
import org.acme.features.market.fruit.application.interaction.FruitDto;
import org.acme.features.market.fruit.application.interaction.command.FruitCreateCommand;
import org.acme.features.market.fruit.application.interaction.command.FruitDeleteCommand;
import org.acme.features.market.fruit.application.interaction.command.FruitUpdateCommand;
import org.acme.features.market.fruit.application.interaction.query.FruitListQuery;
import org.acme.features.market.fruit.application.interaction.query.FruitRetrieveQuery;
import org.acme.features.market.fruit.application.interaction.result.FruitCreateResult;
import org.acme.features.market.fruit.application.interaction.result.FruitDeleteResult;
import org.acme.features.market.fruit.application.interaction.result.FruitListResult;
import org.acme.features.market.fruit.application.interaction.result.FruitRetrieveResult;
import org.acme.features.market.fruit.application.interaction.result.FruitUpdateResult;
import org.acme.features.market.fruit.application.usecase.CreateFruitUsecase;
import org.acme.features.market.fruit.application.usecase.DeleteFruitUsecase;
import org.acme.features.market.fruit.application.usecase.ListFruitUsecase;
import org.acme.features.market.fruit.application.usecase.RetrieveFruitUsecase;
import org.acme.features.market.fruit.application.usecase.UpdateFruitUsecase;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.model.FruitReference;
import org.acme.generated.openapi.api.FruitApi;
import org.acme.generated.openapi.model.Fruit;
import org.acme.generated.openapi.model.FruitList;
import org.acme.generated.openapi.model.FruitListNextOffset;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class FruitController implements FruitApi {

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final CreateFruitUsecase create;

  /**
   * Fruit
   *
   * @autogenerated ApiControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final DeleteFruitUsecase delete;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final ListFruitUsecase list;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final RetrieveFruitUsecase retrieve;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final UpdateFruitUsecase update;

  /**
   * @autogenerated ApiControllerGenerator
   * @param fruit
   * @return
   */
  @Override
  public Response fruitApiCreate(Fruit fruit) {
    return currentRequest.resolve(interaction -> {
      FruitDto dto = toDomainModel(fruit);
      FruitCreateResult result =
          create.create(FruitCreateCommand.builder().dto(dto).build(interaction));
      return result.getFruit().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response fruitApiDelete(final String uid) {
    return currentRequest.resolve(interaction -> {
      FruitDeleteResult result = delete.delete(
          FruitDeleteCommand.builder().reference(FruitReference.of(uid)).build(interaction));
      return result.getFruit().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param uids
   * @param search
   * @param limit
   * @param sinceUid
   * @return
   */
  @Override
  public Response fruitApiList(final String uid, final List<String> uids, final String search,
      final Integer limit, final String sinceUid) {
    return currentRequest.resolve(interaction -> {
      FruitFilter.FruitFilterBuilder filter = FruitFilter.builder();
      FruitCursor.FruitCursorBuilder cursor = FruitCursor.builder();
      cursor = cursor.limit(limit);
      cursor = cursor.sinceUid(sinceUid);
      filter = filter.uid(uid);
      filter = filter.uids(uids);
      filter = filter.search(search);
      FruitListResult result = list.list(FruitListQuery.builder().filter(filter.build())
          .cursor(cursor.build()).build(interaction));
      return result.getFruits()
          .thenApply(fruits -> new FruitList().content(toApiModel(fruits)).next(next(fruits)));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response fruitApiRetrieve(final String uid) {
    return currentRequest.resolve(interaction -> {
      FruitRetrieveResult result = retrieve.retrieve(
          FruitRetrieveQuery.builder().reference(FruitReference.of(uid)).build(interaction));
      return result.getFruit().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param fruit
   * @return
   */
  @Override
  public Response fruitApiUpdate(final String uid, final Fruit fruit) {
    return currentRequest.resolve(interaction -> {
      FruitDto dto = toDomainModel(fruit);
      FruitUpdateResult result = update.update(FruitUpdateCommand.builder().dto(dto)
          .reference(FruitReference.of(uid)).build(interaction));
      return result.getFruit().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param list
   * @return
   */
  private FruitListNextOffset next(List<FruitDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      FruitListNextOffset next = new FruitListNextOffset();
      FruitDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid());
      return next;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param fruits
   * @return
   */
  private List<Fruit> toApiModel(List<FruitDto> fruits) {
    return fruits.stream().map(this::toApiModel).toList();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Fruit toApiModel(FruitDto dto) {
    Fruit fruit = new Fruit();
    fruit.setUid(dto.getUid());
    fruit.setName(dto.getName());
    fruit.setVersion(dto.getVersion());
    return fruit;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param fruit
   * @return
   */
  private FruitDto toDomainModel(Fruit fruit) {
    return FruitDto.builder().uid(fruit.getUid()).name(fruit.getName()).version(fruit.getVersion())
        .build();
  }
}
