package org.acme.features.market.fruit.infrastructure.driver.rest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.acme.common.batch.BatchIdentificator;
import org.acme.common.batch.BatchProgress;
import org.acme.common.infrastructure.CurrentRequest;
import org.acme.features.market.fruit.application.FruitDto;
import org.acme.features.market.fruit.application.usecase.create.CreateFruitUsecase;
import org.acme.features.market.fruit.application.usecase.create.FruitCreateCommand;
import org.acme.features.market.fruit.application.usecase.delete.DeleteFruitUsecase;
import org.acme.features.market.fruit.application.usecase.delete.FruitCheckBatchDeleteStatusQuery;
import org.acme.features.market.fruit.application.usecase.delete.FruitDeleteAllInBatchCommand;
import org.acme.features.market.fruit.application.usecase.delete.FruitDeleteCommand;
import org.acme.features.market.fruit.application.usecase.list.FruitListQuery;
import org.acme.features.market.fruit.application.usecase.list.ListFruitUsecase;
import org.acme.features.market.fruit.application.usecase.retrieve.FruitRetrieveQuery;
import org.acme.features.market.fruit.application.usecase.retrieve.RetrieveFruitUsecase;
import org.acme.features.market.fruit.application.usecase.update.FruitUpdateCommand;
import org.acme.features.market.fruit.application.usecase.update.UpdateFruitUsecase;
import org.acme.features.market.fruit.domain.gateway.FruitCursor;
import org.acme.features.market.fruit.domain.gateway.FruitFilter;
import org.acme.features.market.fruit.domain.gateway.FruitOrder;
import org.acme.features.market.fruit.domain.model.FruitReference;
import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitVersionVO;
import org.acme.generated.openapi.api.FruitApi;
import org.acme.generated.openapi.model.Fruit;
import org.acme.generated.openapi.model.FruitList;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
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
   * @param uids
   * @param search
   * @param name
   * @return
   */
  @Override
  public Response fruitApiBatchDelete(final List<String> uids, final String search,
      final String name) {
    FruitFilter.FruitFilterBuilder filterBuilder = FruitFilter.builder();
    filterBuilder =
        filterBuilder.uids(uids.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    filterBuilder = filterBuilder.search(search);
    filterBuilder =
        filterBuilder.uids(uids.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    filterBuilder = filterBuilder.search(search);
    filterBuilder = filterBuilder.name(name);
    FruitFilter filter = filterBuilder.build();
    BatchIdentificator task = delete.delete(
        FruitDeleteAllInBatchCommand.builder().filter(filter).build(currentRequest.interaction()));
    /* .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME)) */
    return Response.ok(task).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param batchId
   * @return
   */
  @Override
  public Response fruitApiBatchDeleteQuery(final String batchId) {
    BatchProgress task = delete.checkProgress(FruitCheckBatchDeleteStatusQuery.builder()
        .taskId(batchId).build(currentRequest.interaction()));
    return Response.ok(task).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param fruit
   * @return
   */
  @Override
  @Transactional
  public Response fruitApiCreate(Fruit fruit) {
    FruitDto created = create.create(
        FruitCreateCommand.builder().dto(toDomainModel(fruit)).build(currentRequest.interaction()));
    return Response.ok(toApiModel(created)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  @Transactional
  public Response fruitApiDelete(final String uid) {
    FruitDto deleted = delete.delete(FruitDeleteCommand.builder().reference(FruitReference.of(uid))
        .build(currentRequest.interaction()));
    return Response.ok(toApiModel(deleted)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uids
   * @param search
   * @param name
   * @param limit
   * @param sinceUid
   * @param sinceName
   * @param order
   * @return
   */
  @Override
  public Response fruitApiList(final List<String> uids, final String search, final String name,
      final Integer limit, final String sinceUid, final String sinceName, final String order) {
    List<FruitOrder> orderSteps = null == order ? List.of()
        : Arrays.asList(order.split(",")).stream().map(this::mapOrder).filter(Objects::nonNull)
            .toList();
    FruitFilter.FruitFilterBuilder filterBuilder = FruitFilter.builder();
    FruitCursor.FruitCursorBuilder cursorBuilder = FruitCursor.builder();
    filterBuilder =
        filterBuilder.uids(uids.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    filterBuilder = filterBuilder.search(search);
    filterBuilder = filterBuilder.name(name);
    cursorBuilder = cursorBuilder.limit(limit);
    cursorBuilder = cursorBuilder.sinceUid(sinceUid);
    cursorBuilder = cursorBuilder.sinceName(sinceName);
    cursorBuilder = cursorBuilder.order(orderSteps);
    FruitFilter filter = filterBuilder.build();
    FruitCursor cursor = cursorBuilder.build();
    List<FruitDto> listed = list.list(
        FruitListQuery.builder().filter(filter).cursor(cursor).build(currentRequest.interaction()));
    return currentRequest.cacheableResponse(listed, toListApiModel(listed, filter, cursor),
        "fruit-" + String.valueOf(("" + filter + cursor).hashCode()));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response fruitApiRetrieve(final String uid) {
    FruitDto retrieved = retrieve.retrieve(FruitRetrieveQuery.builder()
        .reference(FruitReference.of(uid)).build(currentRequest.interaction()));
    /* .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME))) */
    return Response.ok(toApiModel(retrieved)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param fruit
   * @return
   */
  @Override
  @Transactional
  public Response fruitApiUpdate(final String uid, final Fruit fruit) {
    FruitDto updated = update.update(FruitUpdateCommand.builder().dto(toDomainModel(fruit))
        .reference(FruitReference.of(uid)).build(currentRequest.interaction()));
    return Response.ok(toApiModel(updated)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param label
   * @return
   */
  private FruitOrder mapOrder(final String label) {
    if (null == label) {
      return null;
    } else if (label.trim().equals("name-asc")) {
      return FruitOrder.NAME_ASC;
    } else if (label.trim().equals("name-desc")) {
      return FruitOrder.NAME_DESC;
    } else {
      return null;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Fruit toApiModel(FruitDto dto) {
    Fruit fruit = new Fruit();
    fruit.setUid(Optional.ofNullable(dto.getUid()).map(FruitUidVO::getValue).orElse(null));
    fruit.setName(Optional.ofNullable(dto.getName()).map(FruitNameVO::getValue).orElse(null));
    fruit.setVersion(
        Optional.ofNullable(dto.getVersion()).flatMap(FruitVersionVO::getValue).orElse(null));
    return fruit;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param fruit
   * @return
   */
  private FruitDto toDomainModel(Fruit fruit) {
    FruitDto.FruitDtoBuilder builder = FruitDto.builder();
    if (null != fruit.getUid()) {
      builder = builder.uid(FruitUidVO.from(fruit.getUid()));
    }
    if (null != fruit.getName()) {
      builder = builder.name(FruitNameVO.from(fruit.getName()));
    }
    if (null != fruit.getVersion()) {
      builder = builder.version(FruitVersionVO.from(fruit.getVersion()));
    }
    return builder.build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param fruits
   * @param filter
   * @param cursor
   * @return
   */
  private FruitList toListApiModel(List<FruitDto> fruits, FruitFilter filter, FruitCursor cursor) {
    Optional<FruitDto> last =
        fruits.isEmpty() ? Optional.empty() : Optional.of(fruits.get(fruits.size() - 1));
    StringBuilder self = new StringBuilder();
    StringBuilder next = new StringBuilder();
    StringBuilder first = new StringBuilder();
    if (!filter.getUids().isEmpty()) {
      String uidsValue =
          URLEncoder.encode(String.join(",", filter.getUids()), StandardCharsets.UTF_8);
      self.append("&uids=" + uidsValue);
      next.append("&uids=" + uidsValue);
      first.append("&uids=" + uidsValue);
    }
    filter.getSearch().ifPresent(search -> {
      String searchValue = URLEncoder.encode(search, StandardCharsets.UTF_8);
      self.append("&search=" + searchValue);
      next.append("&search=" + searchValue);
      first.append("&search=" + searchValue);
    });
    filter.getName().ifPresent(filterName -> {
      String nameValue = URLEncoder.encode(String.valueOf(filterName), StandardCharsets.UTF_8);
      self.append("&name=" + nameValue);
      next.append("&name=" + nameValue);
      first.append("&name=" + nameValue);
    });
    cursor.getLimit().ifPresent(limit -> {
      self.append("&limit=" + limit);
      first.append("&limit=" + limit);
      next.append("&limit=" + limit);
    });
    cursor.getSinceUid().ifPresent(
        since -> self.append("&since-uid=" + URLEncoder.encode(since, StandardCharsets.UTF_8)));
    cursor.getSinceName().ifPresent(sinceName -> self
        .append("&since-name=" + URLEncoder.encode(sinceName, StandardCharsets.UTF_8)));
    if (!cursor.getOrder().isEmpty()) {
      String urlOrder = URLEncoder.encode(
          String.join(",",
              cursor.getOrder().stream().map(this::writeOrder).filter(Objects::nonNull).toList()),
          StandardCharsets.UTF_8);
      self.append("&order=" + urlOrder);
      next.append("&order=" + urlOrder);
      first.append("&order=" + urlOrder);
    }
    last.ifPresent(lastDto -> {
      next.append(
          "&since-uid=" + URLEncoder.encode(lastDto.getUid().getValue(), StandardCharsets.UTF_8));
      cursor.getSinceName().ifPresent(sinceName -> next.append(
          "&since-name=" + URLEncoder.encode(lastDto.getUid().getValue(), StandardCharsets.UTF_8)));
    });
    return new FruitList().items(fruits.stream().map(this::toApiModel).toList())
        .next(next.length() > 1 ? "?" + next.substring(1) : "")
        .self(self.length() > 1 ? "?" + self.substring(1) : "")
        .first(first.length() > 1 ? "?" + first.substring(1) : "");
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param order
   * @return
   */
  private String writeOrder(final FruitOrder order) {
    if (null == order) {
      return null;
    } else if (order == FruitOrder.NAME_ASC) {
      return "name-asc";
    } else if (order == FruitOrder.NAME_DESC) {
      return "name-desc";
    } else {
      return null;
    }
  }
}
