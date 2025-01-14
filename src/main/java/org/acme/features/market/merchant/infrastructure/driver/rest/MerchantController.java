package org.acme.features.market.merchant.infrastructure.driver.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.acme.common.rest.CurrentRequest;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.application.usecase.create.CreateMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.create.MerchantCreateCommand;
import org.acme.features.market.merchant.application.usecase.create.MerchantCreateResult;
import org.acme.features.market.merchant.application.usecase.delete.DeleteMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.delete.MerchantDeleteCommand;
import org.acme.features.market.merchant.application.usecase.delete.MerchantDeleteResult;
import org.acme.features.market.merchant.application.usecase.disable.DisableMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.disable.MerchantDisableCommand;
import org.acme.features.market.merchant.application.usecase.disable.MerchantDisableResult;
import org.acme.features.market.merchant.application.usecase.enable.EnableMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.enable.MerchantEnableCommand;
import org.acme.features.market.merchant.application.usecase.enable.MerchantEnableResult;
import org.acme.features.market.merchant.application.usecase.list.ListMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.list.MerchantListQuery;
import org.acme.features.market.merchant.application.usecase.list.MerchantListResult;
import org.acme.features.market.merchant.application.usecase.retrieve.MerchantRetrieveQuery;
import org.acme.features.market.merchant.application.usecase.retrieve.MerchantRetrieveResult;
import org.acme.features.market.merchant.application.usecase.retrieve.RetrieveMerchantUsecase;
import org.acme.features.market.merchant.application.usecase.update.MerchantUpdateCommand;
import org.acme.features.market.merchant.application.usecase.update.MerchantUpdateResult;
import org.acme.features.market.merchant.application.usecase.update.UpdateMerchantUsecase;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.gateway.MerchantFilter;
import org.acme.features.market.merchant.domain.gateway.MerchantOrder;
import org.acme.features.market.merchant.domain.model.MerchantReference;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantEnabledVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantKeyVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantNameVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantUidVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantVersionVO;
import org.acme.generated.openapi.api.MerchantApi;
import org.acme.generated.openapi.model.Merchant;
import org.acme.generated.openapi.model.MerchantList;
import org.acme.generated.openapi.model.MerchantListNextOffset;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MerchantController implements MerchantApi {

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final CreateMerchantUsecase create;

  /**
   * Merchant
   *
   * @autogenerated ApiControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final DeleteMerchantUsecase delete;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final DisableMerchantUsecase disable;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final EnableMerchantUsecase enable;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final ListMerchantUsecase list;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final RetrieveMerchantUsecase retrieve;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final UpdateMerchantUsecase update;

  /**
   * @autogenerated ApiControllerGenerator
   * @param merchant
   * @return
   */
  @Override
  public Response merchantApiCreate(Merchant merchant) {
    return currentRequest.resolve(interaction -> {
      MerchantDto dto = toDomainModel(merchant);
      MerchantCreateResult result =
          create.create(MerchantCreateCommand.builder().dto(dto).build(interaction));
      return result.getMerchant().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response merchantApiDelete(final String uid) {
    return currentRequest.resolve(interaction -> {
      MerchantDeleteResult result = delete.delete(
          MerchantDeleteCommand.builder().reference(MerchantReference.of(uid)).build(interaction));
      return result.getMerchant().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response merchantApiDisable(final String uid) {
    return currentRequest.resolve(interaction -> {
      MerchantDisableResult result = disable.disable(
          MerchantDisableCommand.builder().reference(MerchantReference.of(uid)).build(interaction));
      return result.getMerchant().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response merchantApiEnable(final String uid) {
    return currentRequest.resolve(interaction -> {
      MerchantEnableResult result = enable.enable(
          MerchantEnableCommand.builder().reference(MerchantReference.of(uid)).build(interaction));
      return result.getMerchant().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param uids
   * @param search
   * @param enabled
   * @param limit
   * @param sinceUid
   * @param order
   * @return
   */
  @Override
  public Response merchantApiList(final String uid, final List<String> uids, final String search,
      final Boolean enabled, final Integer limit, final String sinceUid, final String order) {
    return currentRequest.resolve(interaction -> {
      MerchantFilter.MerchantFilterBuilder filter = MerchantFilter.builder();
      MerchantCursor.MerchantCursorBuilder cursor = MerchantCursor.builder();
      cursor = cursor.limit(limit);
      cursor = cursor.sinceUid(sinceUid);
      filter = filter.uid(uid);
      filter = filter.uids(uids);
      filter = filter.search(search);
      filter = filter.enabled(enabled);
      if (null != order) {
        cursor = cursor.order(Arrays.asList(order.split(",")).stream().map(this::mapOrder)
            .filter(Objects::nonNull).toList());
      }
      MerchantListResult result = list.list(MerchantListQuery.builder().filter(filter.build())
          .cursor(cursor.build()).build(interaction));
      return result.getMerchants().thenApply(
          merchants -> new MerchantList().content(toApiModel(merchants)).next(next(merchants)));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response merchantApiRetrieve(final String uid) {
    return currentRequest.resolve(interaction -> {
      MerchantRetrieveResult result = retrieve.retrieve(
          MerchantRetrieveQuery.builder().reference(MerchantReference.of(uid)).build(interaction));
      return result.getMerchant().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param merchant
   * @return
   */
  @Override
  public Response merchantApiUpdate(final String uid, final Merchant merchant) {
    return currentRequest.resolve(interaction -> {
      MerchantDto dto = toDomainModel(merchant);
      MerchantUpdateResult result = update.update(MerchantUpdateCommand.builder().dto(dto)
          .reference(MerchantReference.of(uid)).build(interaction));
      return result.getMerchant().thenApply(res -> res.map(this::toApiModel));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param label
   * @return
   */
  private MerchantOrder mapOrder(final String label) {
    if (null == label) {
      return null;
    } else if (label.trim().equals("name-asc")) {
      return MerchantOrder.NAME_ASC;
    } else if (label.trim().equals("name-desc")) {
      return MerchantOrder.NAME_DESC;
    } else {
      return null;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param list
   * @return
   */
  private MerchantListNextOffset next(List<MerchantDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      MerchantListNextOffset next = new MerchantListNextOffset();
      MerchantDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid().getValue());
      return next;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param merchants
   * @return
   */
  private List<Merchant> toApiModel(List<MerchantDto> merchants) {
    return merchants.stream().map(this::toApiModel).toList();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Merchant toApiModel(MerchantDto dto) {
    Merchant merchant = new Merchant();
    merchant.setUid(Optional.ofNullable(dto.getUid()).map(MerchantUidVO::getValue).orElse(null));
    merchant.setName(Optional.ofNullable(dto.getName()).map(MerchantNameVO::getValue).orElse(null));
    merchant.setEnabled(
        Optional.ofNullable(dto.getEnabled()).map(MerchantEnabledVO::getValue).orElse(null));
    merchant
        .setKey(Optional.ofNullable(dto.getKey()).flatMap(MerchantKeyVO::getValue).orElse(null));
    merchant.setVersion(
        Optional.ofNullable(dto.getVersion()).flatMap(MerchantVersionVO::getValue).orElse(null));
    return merchant;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param merchant
   * @return
   */
  private MerchantDto toDomainModel(Merchant merchant) {
    MerchantDto.MerchantDtoBuilder builder = MerchantDto.builder();
    if (null != merchant.getUid()) {
      builder = builder.uid(MerchantUidVO.from(merchant.getUid()));
    }
    if (null != merchant.getName()) {
      builder = builder.name(MerchantNameVO.from(merchant.getName()));
    }
    if (null != merchant.getEnabled()) {
      builder = builder.enabled(MerchantEnabledVO.from(merchant.getEnabled()));
    }
    if (null != merchant.getKey()) {
      builder = builder.key(MerchantKeyVO.from(merchant.getKey()));
    }
    if (null != merchant.getVersion()) {
      builder = builder.version(MerchantVersionVO.from(merchant.getVersion()));
    } ;
    return builder.build();
  }
}
