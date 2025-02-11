package org.acme.features.market.color.infrastructure.driver.rest;

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
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.usecase.create.ColorCreateCommand;
import org.acme.features.market.color.application.usecase.create.CreateColorUsecase;
import org.acme.features.market.color.application.usecase.delete.ColorCheckBatchDeleteStatusQuery;
import org.acme.features.market.color.application.usecase.delete.ColorDeleteAllInBatchCommand;
import org.acme.features.market.color.application.usecase.delete.ColorDeleteCommand;
import org.acme.features.market.color.application.usecase.delete.DeleteColorUsecase;
import org.acme.features.market.color.application.usecase.list.ColorListQuery;
import org.acme.features.market.color.application.usecase.list.ListColorUsecase;
import org.acme.features.market.color.application.usecase.retrieve.ColorRetrieveQuery;
import org.acme.features.market.color.application.usecase.retrieve.RetrieveColorUsecase;
import org.acme.features.market.color.application.usecase.update.ColorUpdateCommand;
import org.acme.features.market.color.application.usecase.update.UpdateColorUsecase;
import org.acme.features.market.color.domain.gateway.ColorCursor;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.gateway.ColorOrder;
import org.acme.features.market.color.domain.model.ColorReference;
import org.acme.features.market.color.domain.model.valueobject.ColorMerchantVO;
import org.acme.features.market.color.domain.model.valueobject.ColorNameVO;
import org.acme.features.market.color.domain.model.valueobject.ColorUidVO;
import org.acme.features.market.color.domain.model.valueobject.ColorVersionVO;
import org.acme.generated.openapi.api.ColorApi;
import org.acme.generated.openapi.model.Color;
import org.acme.generated.openapi.model.ColorList;
import org.acme.generated.openapi.model.MerchantRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ColorController implements ColorApi {

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final CreateColorUsecase create;

  /**
   * Color
   *
   * @autogenerated ApiControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final DeleteColorUsecase delete;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final ListColorUsecase list;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final RetrieveColorUsecase retrieve;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final UpdateColorUsecase update;

  /**
   * @autogenerated ApiControllerGenerator
   * @param uids
   * @param search
   * @param name
   * @param merchants
   * @return
   */
  @Override
  public Response colorApiBatchDelete(final List<String> uids, final String search,
      final String name, final List<String> merchants) {
    ColorFilter.ColorFilterBuilder filterBuilder = ColorFilter.builder();
    filterBuilder =
        filterBuilder.uids(uids.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    filterBuilder = filterBuilder.search(search);
    filterBuilder =
        filterBuilder.uids(uids.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    filterBuilder = filterBuilder.search(search);
    filterBuilder = filterBuilder.name(name);
    filterBuilder = filterBuilder
        .merchants(merchants.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    ColorFilter filter = filterBuilder.build();
    BatchIdentificator task = delete.delete(
        ColorDeleteAllInBatchCommand.builder().filter(filter).build(currentRequest.interaction()));
    /* .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME)) */
    return Response.ok(task).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param batchId
   * @return
   */
  @Override
  public Response colorApiBatchDeleteQuery(final String batchId) {
    BatchProgress task = delete.checkProgress(ColorCheckBatchDeleteStatusQuery.builder()
        .taskId(batchId).build(currentRequest.interaction()));
    return Response.ok(task).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param color
   * @return
   */
  @Override
  @Transactional
  public Response colorApiCreate(Color color) {
    ColorDto created = create.create(
        ColorCreateCommand.builder().dto(toDomainModel(color)).build(currentRequest.interaction()));
    return Response.ok(toApiModel(created)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  @Transactional
  public Response colorApiDelete(final String uid) {
    ColorDto deleted = delete.delete(ColorDeleteCommand.builder().reference(ColorReference.of(uid))
        .build(currentRequest.interaction()));
    return Response.ok(toApiModel(deleted)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uids
   * @param search
   * @param name
   * @param merchants
   * @param limit
   * @param sinceUid
   * @param sinceName
   * @param order
   * @return
   */
  @Override
  public Response colorApiList(final List<String> uids, final String search, final String name,
      final List<String> merchants, final Integer limit, final String sinceUid,
      final String sinceName, final String order) {
    List<ColorOrder> orderSteps = null == order ? List.of()
        : Arrays.asList(order.split(",")).stream().map(this::mapOrder).filter(Objects::nonNull)
            .toList();
    ColorFilter.ColorFilterBuilder filterBuilder = ColorFilter.builder();
    ColorCursor.ColorCursorBuilder cursorBuilder = ColorCursor.builder();
    filterBuilder =
        filterBuilder.uids(uids.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    filterBuilder = filterBuilder.search(search);
    filterBuilder = filterBuilder.name(name);
    filterBuilder = filterBuilder
        .merchants(merchants.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    cursorBuilder = cursorBuilder.limit(limit);
    cursorBuilder = cursorBuilder.sinceUid(sinceUid);
    cursorBuilder = cursorBuilder.sinceName(sinceName);
    cursorBuilder = cursorBuilder.order(orderSteps);
    ColorFilter filter = filterBuilder.build();
    ColorCursor cursor = cursorBuilder.build();
    List<ColorDto> listed = list.list(
        ColorListQuery.builder().filter(filter).cursor(cursor).build(currentRequest.interaction()));
    return currentRequest.cacheableResponse(listed, toListApiModel(listed, filter, cursor),
        "color-" + String.valueOf(("" + filter + cursor).hashCode()));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response colorApiRetrieve(final String uid) {
    ColorDto retrieved = retrieve.retrieve(ColorRetrieveQuery.builder()
        .reference(ColorReference.of(uid)).build(currentRequest.interaction()));
    /* .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME))) */
    return Response.ok(toApiModel(retrieved)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param color
   * @return
   */
  @Override
  @Transactional
  public Response colorApiUpdate(final String uid, final Color color) {
    ColorDto updated = update.update(ColorUpdateCommand.builder().dto(toDomainModel(color))
        .reference(ColorReference.of(uid)).build(currentRequest.interaction()));
    return Response.ok(toApiModel(updated)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param label
   * @return
   */
  private ColorOrder mapOrder(final String label) {
    if (null == label) {
      return null;
    } else if (label.trim().equals("name-asc")) {
      return ColorOrder.NAME_ASC;
    } else if (label.trim().equals("name-desc")) {
      return ColorOrder.NAME_DESC;
    } else {
      return null;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Color toApiModel(ColorDto dto) {
    Color color = new Color();
    color.setUid(Optional.ofNullable(dto.getUid()).map(ColorUidVO::getValue).orElse(null));
    color.setName(Optional.ofNullable(dto.getName()).map(ColorNameVO::getValue).orElse(null));
    color.setMerchant(new MerchantRef().$ref(Optional.ofNullable(dto.getMerchant())
        .flatMap(ColorMerchantVO::getReferenceValue).orElse(null)));
    color.setVersion(
        Optional.ofNullable(dto.getVersion()).flatMap(ColorVersionVO::getValue).orElse(null));
    return color;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param color
   * @return
   */
  private ColorDto toDomainModel(Color color) {
    ColorDto.ColorDtoBuilder builder = ColorDto.builder();
    if (null != color.getUid()) {
      builder = builder.uid(ColorUidVO.from(color.getUid()));
    }
    if (null != color.getName()) {
      builder = builder.name(ColorNameVO.from(color.getName()));
    }
    if (null != color.getMerchant()) {
      builder = builder.merchant(ColorMerchantVO.fromReference(
          Optional.ofNullable(color.getMerchant()).map(MerchantRef::get$Ref).orElse(null)));
    }
    if (null != color.getVersion()) {
      builder = builder.version(ColorVersionVO.from(color.getVersion()));
    }
    return builder.build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param colors
   * @param filter
   * @param cursor
   * @return
   */
  private ColorList toListApiModel(List<ColorDto> colors, ColorFilter filter, ColorCursor cursor) {
    Optional<ColorDto> last =
        colors.isEmpty() ? Optional.empty() : Optional.of(colors.get(colors.size() - 1));
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
    if (!filter.getMerchants().isEmpty()) {
      String merchantsValue =
          URLEncoder.encode(String.join(",", filter.getMerchants()), StandardCharsets.UTF_8);
      self.append("&merchants=" + merchantsValue);
      next.append("&merchants=" + merchantsValue);
      first.append("&merchants=" + merchantsValue);
    }
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
    return new ColorList().items(colors.stream().map(this::toApiModel).toList())
        .next(next.length() > 1 ? "?" + next.substring(1) : "")
        .self(self.length() > 1 ? "?" + self.substring(1) : "")
        .first(first.length() > 1 ? "?" + first.substring(1) : "");
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param order
   * @return
   */
  private String writeOrder(final ColorOrder order) {
    if (null == order) {
      return null;
    } else if (order == ColorOrder.NAME_ASC) {
      return "name-asc";
    } else if (order == ColorOrder.NAME_DESC) {
      return "name-desc";
    } else {
      return null;
    }
  }
}
