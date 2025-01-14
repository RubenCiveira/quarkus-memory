package org.acme.features.market.color.infrastructure.driver.rest;

import java.util.List;
import java.util.Optional;

import org.acme.common.rest.CurrentRequest;
import org.acme.features.market.color.application.ColorDto;
import org.acme.features.market.color.application.usecase.create.ColorCreateCommand;
import org.acme.features.market.color.application.usecase.create.ColorCreateResult;
import org.acme.features.market.color.application.usecase.create.CreateColorUsecase;
import org.acme.features.market.color.application.usecase.delete.ColorDeleteCommand;
import org.acme.features.market.color.application.usecase.delete.ColorDeleteResult;
import org.acme.features.market.color.application.usecase.delete.DeleteColorUsecase;
import org.acme.features.market.color.application.usecase.list.ColorListQuery;
import org.acme.features.market.color.application.usecase.list.ColorListResult;
import org.acme.features.market.color.application.usecase.list.ListColorUsecase;
import org.acme.features.market.color.application.usecase.retrieve.ColorRetrieveQuery;
import org.acme.features.market.color.application.usecase.retrieve.ColorRetrieveResult;
import org.acme.features.market.color.application.usecase.retrieve.RetrieveColorUsecase;
import org.acme.features.market.color.application.usecase.update.ColorUpdateCommand;
import org.acme.features.market.color.application.usecase.update.ColorUpdateResult;
import org.acme.features.market.color.application.usecase.update.UpdateColorUsecase;
import org.acme.features.market.color.domain.gateway.ColorCursor;
import org.acme.features.market.color.domain.gateway.ColorFilter;
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
  public Response colorApiCreate(Color color) {
    return currentRequest.resolve(interaction -> {
      ColorDto dto = toDomainModel(color);
      ColorCreateResult result =
          create.create(ColorCreateCommand.builder().dto(dto).build(interaction));
      return result.getColor().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response colorApiDelete(final String uid) {
    return currentRequest.resolve(interaction -> {
      ColorDeleteResult result = delete.delete(
          ColorDeleteCommand.builder().reference(ColorReference.of(uid)).build(interaction));
      return result.getColor().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param uids
   * @param search
   * @param merchant
   * @param limit
   * @param sinceUid
   * @return
   */
  @Override
  public Response colorApiList(final String uid, final List<String> uids, final String search,
      final String merchant, final Integer limit, final String sinceUid) {
    return currentRequest.resolve(interaction -> {
      ColorFilter.ColorFilterBuilder filter = ColorFilter.builder();
      ColorCursor.ColorCursorBuilder cursor = ColorCursor.builder();
      cursor = cursor.limit(limit);
      cursor = cursor.sinceUid(sinceUid);
      filter = filter.uid(uid);
      filter = filter.uids(uids);
      filter = filter.search(search);
      if (null != merchant) {
        filter = filter.merchant(MerchantReference.of(merchant));
      }
      ColorListResult result = list.list(ColorListQuery.builder().filter(filter.build())
          .cursor(cursor.build()).build(interaction));
      return result.getColors()
          .thenApply(colors -> new ColorList().content(toApiModel(colors)).next(next(colors)));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response colorApiRetrieve(final String uid) {
    return currentRequest.resolve(interaction -> {
      ColorRetrieveResult result = retrieve.retrieve(
          ColorRetrieveQuery.builder().reference(ColorReference.of(uid)).build(interaction));
      return result.getColor().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param color
   * @return
   */
  @Override
  public Response colorApiUpdate(final String uid, final Color color) {
    return currentRequest.resolve(interaction -> {
      ColorDto dto = toDomainModel(color);
      ColorUpdateResult result = update.update(ColorUpdateCommand.builder().dto(dto)
          .reference(ColorReference.of(uid)).build(interaction));
      return result.getColor().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param list
   * @return
   */
  private ColorListNextOffset next(List<ColorDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      ColorListNextOffset next = new ColorListNextOffset();
      ColorDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid().getValue());
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
    } ;
    return builder.build();
  }
}
