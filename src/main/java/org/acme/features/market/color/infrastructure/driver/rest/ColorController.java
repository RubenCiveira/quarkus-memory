package org.acme.features.market.color.infrastructure.driver.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.acme.common.infrastructure.CurrentRequest;
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.usecase.create.ColorCreateCommand;
import org.acme.features.market.color.application.usecase.create.CreateColorUsecase;
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
import org.acme.features.market.merchant.domain.model.MerchantReference;
import org.acme.generated.openapi.api.ColorApi;
import org.acme.generated.openapi.model.Color;
import org.acme.generated.openapi.model.ColorList;
import org.acme.generated.openapi.model.ColorListNextOffset;
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
   * @param uid
   * @param uids
   * @param search
   * @param name
   * @param merchant
   * @param limit
   * @param sinceUid
   * @param sinceName
   * @param order
   * @return
   */
  @Override
  public Response colorApiList(final String uid, final List<String> uids, final String search,
      final String name, final String merchant, final Integer limit, final String sinceUid,
      final String sinceName, final String order) {
    List<ColorOrder> orderSteps = null == order ? List.of()
        : Arrays.asList(order.split(",")).stream().map(this::mapOrder).filter(Objects::nonNull)
            .toList();
    ColorFilter.ColorFilterBuilder filter = ColorFilter.builder();
    ColorCursor.ColorCursorBuilder cursor = ColorCursor.builder();
    cursor = cursor.limit(limit);
    cursor = cursor.sinceUid(sinceUid);
    filter = filter.uid(uid);
    filter = filter.uids(uids);
    filter = filter.search(search);
    filter = filter.name(name);
    if (null != merchant) {
      filter = filter.merchant(MerchantReference.of(merchant));
    }
    cursor = cursor.sinceName(sinceName);
    cursor = cursor.order(orderSteps);
    List<ColorDto> listed = list.list(ColorListQuery.builder().filter(filter.build())
        .cursor(cursor.build()).build(currentRequest.interaction()));
    /* .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME)) */
    return Response.ok(new ColorList().content(toApiModel(listed)).next(next(orderSteps, listed)))
        .build();
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
   * @param order
   * @param list
   * @return
   */
  private ColorListNextOffset next(List<ColorOrder> order, List<ColorDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      ColorListNextOffset next = new ColorListNextOffset();
      ColorDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid().getValue());
      if (order.contains(ColorOrder.NAME_ASC)) {
        next.setSinceName(last.getName().getValue());
      }
      if (order.contains(ColorOrder.NAME_DESC)) {
        next.setSinceName(last.getName().getValue());
      }
      return next;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param colors
   * @return
   */
  private List<Color> toApiModel(List<ColorDto> colors) {
    return colors.stream().map(this::toApiModel).toList();
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
}
