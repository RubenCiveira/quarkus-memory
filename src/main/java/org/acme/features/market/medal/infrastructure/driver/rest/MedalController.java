package org.acme.features.market.medal.infrastructure.driver.rest;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.acme.common.rest.CurrentRequest;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.usecase.create.CreateMedalUsecase;
import org.acme.features.market.medal.application.usecase.create.MedalCreateCommand;
import org.acme.features.market.medal.application.usecase.delete.DeleteMedalUsecase;
import org.acme.features.market.medal.application.usecase.delete.MedalDeleteCommand;
import org.acme.features.market.medal.application.usecase.list.ListMedalUsecase;
import org.acme.features.market.medal.application.usecase.list.MedalListQuery;
import org.acme.features.market.medal.application.usecase.retrieve.MedalRetrieveQuery;
import org.acme.features.market.medal.application.usecase.retrieve.RetrieveMedalUsecase;
import org.acme.features.market.medal.application.usecase.update.MedalUpdateCommand;
import org.acme.features.market.medal.application.usecase.update.UpdateMedalUsecase;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.model.MedalReference;
import org.acme.features.market.medal.domain.model.valueobject.MedalNameVO;
import org.acme.features.market.medal.domain.model.valueobject.MedalUidVO;
import org.acme.features.market.medal.domain.model.valueobject.MedalVersionVO;
import org.acme.generated.openapi.api.MedalApi;
import org.acme.generated.openapi.model.Medal;
import org.acme.generated.openapi.model.MedalList;
import org.acme.generated.openapi.model.MedalListNextOffset;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MedalController implements MedalApi {

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final CreateMedalUsecase create;

  /**
   * Medal
   *
   * @autogenerated ApiControllerGenerator
   */
  private final CurrentRequest currentRequest;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final DeleteMedalUsecase delete;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final ListMedalUsecase list;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final RetrieveMedalUsecase retrieve;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final UpdateMedalUsecase update;

  /**
   * @autogenerated ApiControllerGenerator
   * @param medal
   * @return
   */
  @Override
  @Transactional
  public Response medalApiCreate(Medal medal) {
    return currentRequest.resolve(interaction -> create
        .create(MedalCreateCommand.builder().dto(toDomainModel(medal)).build(interaction))
        .thenApply(res -> res.getMedal().map(this::toApiModel)));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  @Transactional
  public Response medalApiDelete(final String uid) {
    return currentRequest.resolve(interaction -> delete
        .delete(MedalDeleteCommand.builder().reference(MedalReference.of(uid)).build(interaction))
        .thenApply(res -> res.getMedal().map(this::toApiModel)));
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
  public Response medalApiList(final String uid, final List<String> uids, final String search,
      final Integer limit, final String sinceUid) {
    return currentRequest.resolve(interaction -> {
      MedalFilter.MedalFilterBuilder filter = MedalFilter.builder();
      MedalCursor.MedalCursorBuilder cursor = MedalCursor.builder();
      cursor = cursor.limit(limit);
      cursor = cursor.sinceUid(sinceUid);
      filter = filter.uid(uid);
      filter = filter.uids(uids);
      filter = filter.search(search);
      return list.list(MedalListQuery.builder().filter(filter.build()).cursor(cursor.build())
          .build(interaction));
    }, value -> Response
        .ok(new MedalList().content(toApiModel(value.getMedals())).next(next(value.getMedals())))
        .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME))
        .build());
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response medalApiRetrieve(final String uid) {
    return currentRequest.resolve(
        interaction -> retrieve.retrieve(
            MedalRetrieveQuery.builder().reference(MedalReference.of(uid)).build(interaction)),
        value -> value.getMedal()
            .map(medal -> Response.ok(toApiModel(medal)).header("Last-Modified",
                value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME)))
            .orElseGet(() -> Response.status(404)).build());
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param medal
   * @return
   */
  @Override
  @Transactional
  public Response medalApiUpdate(final String uid, final Medal medal) {
    return currentRequest.resolve(interaction -> update
        .update(MedalUpdateCommand.builder().dto(toDomainModel(medal))
            .reference(MedalReference.of(uid)).build(interaction))
        .thenApply(res -> res.getMedal().map(this::toApiModel)));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param list
   * @return
   */
  private MedalListNextOffset next(List<MedalDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      MedalListNextOffset next = new MedalListNextOffset();
      MedalDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid().getValue());
      return next;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param medals
   * @return
   */
  private List<Medal> toApiModel(List<MedalDto> medals) {
    return medals.stream().map(this::toApiModel).toList();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Medal toApiModel(MedalDto dto) {
    Medal medal = new Medal();
    medal.setUid(Optional.ofNullable(dto.getUid()).map(MedalUidVO::getValue).orElse(null));
    medal.setName(Optional.ofNullable(dto.getName()).map(MedalNameVO::getValue).orElse(null));
    medal.setVersion(
        Optional.ofNullable(dto.getVersion()).flatMap(MedalVersionVO::getValue).orElse(null));
    return medal;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param medal
   * @return
   */
  private MedalDto toDomainModel(Medal medal) {
    MedalDto.MedalDtoBuilder builder = MedalDto.builder();
    if (null != medal.getUid()) {
      builder = builder.uid(MedalUidVO.from(medal.getUid()));
    }
    if (null != medal.getName()) {
      builder = builder.name(MedalNameVO.from(medal.getName()));
    }
    if (null != medal.getVersion()) {
      builder = builder.version(MedalVersionVO.from(medal.getVersion()));
    } ;
    return builder.build();
  }
}
