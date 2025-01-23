package org.acme.features.market.verify.infrastructure.driver.rest;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.acme.common.rest.CurrentRequest;
import org.acme.features.market.verify.application.VerifyDto;
import org.acme.features.market.verify.application.usecase.create.CreateVerifyUsecase;
import org.acme.features.market.verify.application.usecase.create.VerifyCreateCommand;
import org.acme.features.market.verify.application.usecase.delete.DeleteVerifyUsecase;
import org.acme.features.market.verify.application.usecase.delete.VerifyDeleteCommand;
import org.acme.features.market.verify.application.usecase.list.ListVerifyUsecase;
import org.acme.features.market.verify.application.usecase.list.VerifyListQuery;
import org.acme.features.market.verify.application.usecase.retrieve.RetrieveVerifyUsecase;
import org.acme.features.market.verify.application.usecase.retrieve.VerifyRetrieveQuery;
import org.acme.features.market.verify.application.usecase.update.UpdateVerifyUsecase;
import org.acme.features.market.verify.application.usecase.update.VerifyUpdateCommand;
import org.acme.features.market.verify.domain.gateway.VerifyCursor;
import org.acme.features.market.verify.domain.gateway.VerifyFilter;
import org.acme.features.market.verify.domain.gateway.VerifyOrder;
import org.acme.features.market.verify.domain.model.VerifyMedal;
import org.acme.features.market.verify.domain.model.VerifyReference;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalMedalVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalUidVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalVersionVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalsVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyNameVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyUidVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyVersionVO;
import org.acme.generated.openapi.api.VerifyApi;
import org.acme.generated.openapi.model.MedalRef;
import org.acme.generated.openapi.model.Medals;
import org.acme.generated.openapi.model.Verify;
import org.acme.generated.openapi.model.VerifyList;
import org.acme.generated.openapi.model.VerifyListNextOffset;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class VerifyController implements VerifyApi {

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final CreateVerifyUsecase create;

  /**
   * Verify
   *
   * @autogenerated ApiControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final DeleteVerifyUsecase delete;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final ListVerifyUsecase list;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final RetrieveVerifyUsecase retrieve;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final UpdateVerifyUsecase update;

  /**
   * @autogenerated ApiControllerGenerator
   * @param verify
   * @return
   */
  @Override
  @Transactional
  public Response verifyApiCreate(Verify verify) {
    return currentRequest.resolve(interaction -> create
        .create(VerifyCreateCommand.builder().dto(toDomainModel(verify)).build(interaction))
        .thenApply(res -> res.getVerify().map(this::toApiModel)));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  @Transactional
  public Response verifyApiDelete(final String uid) {
    return currentRequest.resolve(interaction -> delete
        .delete(VerifyDeleteCommand.builder().reference(VerifyReference.of(uid)).build(interaction))
        .thenApply(res -> res.getVerify().map(this::toApiModel)));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
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
  public Response verifyApiList(final String uid, final List<String> uids, final String search,
      final String name, final Integer limit, final String sinceUid, final String sinceName,
      final String order) {
    List<VerifyOrder> orderSteps = null == order ? List.of()
        : Arrays.asList(order.split(",")).stream().map(this::mapOrder).filter(Objects::nonNull)
            .toList();
    return currentRequest.resolve(interaction -> {
      VerifyFilter.VerifyFilterBuilder filter = VerifyFilter.builder();
      VerifyCursor.VerifyCursorBuilder cursor = VerifyCursor.builder();
      cursor = cursor.limit(limit);
      cursor = cursor.sinceUid(sinceUid);
      filter = filter.uid(uid);
      filter = filter.uids(uids);
      filter = filter.search(search);
      filter = filter.name(name);
      cursor = cursor.sinceName(sinceName);
      cursor = cursor.order(orderSteps);
      return list.list(VerifyListQuery.builder().filter(filter.build()).cursor(cursor.build())
          .build(interaction));
    }, value -> Response
        .ok(new VerifyList().content(toApiModel(value.getVerifys()))
            .next(next(orderSteps, value.getVerifys())))
        .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME))
        .build());
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response verifyApiRetrieve(final String uid) {
    return currentRequest.resolve(
        interaction -> retrieve.retrieve(
            VerifyRetrieveQuery.builder().reference(VerifyReference.of(uid)).build(interaction)),
        value -> value.getVerify()
            .map(verify -> Response.ok(toApiModel(verify)).header("Last-Modified",
                value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME)))
            .orElseGet(() -> Response.status(404)).build());
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param verify
   * @return
   */
  @Override
  @Transactional
  public Response verifyApiUpdate(final String uid, final Verify verify) {
    return currentRequest.resolve(interaction -> update
        .update(VerifyUpdateCommand.builder().dto(toDomainModel(verify))
            .reference(VerifyReference.of(uid)).build(interaction))
        .thenApply(res -> res.getVerify().map(this::toApiModel)));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param label
   * @return
   */
  private VerifyOrder mapOrder(final String label) {
    if (null == label) {
      return null;
    } else if (label.trim().equals("name-asc")) {
      return VerifyOrder.NAME_ASC;
    } else if (label.trim().equals("name-desc")) {
      return VerifyOrder.NAME_DESC;
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
  private VerifyListNextOffset next(List<VerifyOrder> order, List<VerifyDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      VerifyListNextOffset next = new VerifyListNextOffset();
      VerifyDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid().getValue());
      if (order.contains(VerifyOrder.NAME_ASC)) {
        next.setSinceName(last.getName().getValue());
      }
      if (order.contains(VerifyOrder.NAME_DESC)) {
        next.setSinceName(last.getName().getValue());
      }
      return next;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param verifys
   * @return
   */
  private List<Verify> toApiModel(List<VerifyDto> verifys) {
    return verifys.stream().map(this::toApiModel).toList();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Verify toApiModel(VerifyDto dto) {
    Verify verify = new Verify();
    verify.setUid(Optional.ofNullable(dto.getUid()).map(VerifyUidVO::getValue).orElse(null));
    verify.setName(Optional.ofNullable(dto.getName()).map(VerifyNameVO::getValue).orElse(null));
    verify.setMedals(Optional.ofNullable(dto.getMedals()).map(VerifyMedalsVO::getValue)
        .map(from -> from.stream().map(this::toApiModelMedals).toList()).orElse(null));
    verify.setVersion(
        Optional.ofNullable(dto.getVersion()).flatMap(VerifyVersionVO::getValue).orElse(null));
    return verify;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Medals toApiModelMedals(VerifyMedal dto) {
    Medals medals = new Medals();
    medals.setUid(Optional.ofNullable(dto.getUid()).map(VerifyMedalUidVO::getValue).orElse(null));
    medals.setMedal(new MedalRef().$ref(Optional.ofNullable(dto.getMedal())
        .map(VerifyMedalMedalVO::getReferenceValue).orElse(null)));
    medals.setVersion(
        Optional.ofNullable(dto.getVersion()).flatMap(VerifyMedalVersionVO::getValue).orElse(null));
    return medals;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param verify
   * @return
   */
  private VerifyDto toDomainModel(Verify verify) {
    VerifyDto.VerifyDtoBuilder builder = VerifyDto.builder();
    if (null != verify.getUid()) {
      builder = builder.uid(VerifyUidVO.from(verify.getUid()));
    }
    if (null != verify.getName()) {
      builder = builder.name(VerifyNameVO.from(verify.getName()));
    }
    if (null != verify.getMedals()) {
      builder = builder.medals(
          VerifyMedalsVO.from(verify.getMedals().stream().map(this::toDomainModelMedals).toList()));
    }
    if (null != verify.getVersion()) {
      builder = builder.version(VerifyVersionVO.from(verify.getVersion()));
    }
    return builder.build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param medals
   * @return
   */
  private VerifyMedal toDomainModelMedals(Medals medals) {
    VerifyMedal.VerifyMedalBuilder builder = VerifyMedal.builder();
    if (null != medals.getUid()) {
      builder = builder.uid(VerifyMedalUidVO.from(medals.getUid()));
    }
    if (null != medals.getMedal()) {
      builder = builder.medal(VerifyMedalMedalVO.fromReference(
          Optional.ofNullable(medals.getMedal()).map(MedalRef::get$Ref).orElse(null)));
    }
    if (null != medals.getVersion()) {
      builder = builder.version(VerifyMedalVersionVO.from(medals.getVersion()));
    }
    return builder.build();
  }
}
