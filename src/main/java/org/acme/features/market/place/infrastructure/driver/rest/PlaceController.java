package org.acme.features.market.place.infrastructure.driver.rest;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.market.merchant.domain.model.MerchantReference;
import org.acme.features.market.place.application.interaction.PlaceDto;
import org.acme.features.market.place.application.interaction.command.PlaceCreateCommand;
import org.acme.features.market.place.application.interaction.command.PlaceDeleteCommand;
import org.acme.features.market.place.application.interaction.command.PlaceUpdateCommand;
import org.acme.features.market.place.application.interaction.query.PlaceListQuery;
import org.acme.features.market.place.application.interaction.query.PlaceRetrieveQuery;
import org.acme.features.market.place.application.interaction.result.PlaceCreateResult;
import org.acme.features.market.place.application.interaction.result.PlaceDeleteResult;
import org.acme.features.market.place.application.interaction.result.PlaceListResult;
import org.acme.features.market.place.application.interaction.result.PlaceRetrieveResult;
import org.acme.features.market.place.application.interaction.result.PlaceUpdateResult;
import org.acme.features.market.place.application.usecase.CreatePlaceUsecase;
import org.acme.features.market.place.application.usecase.DeletePlaceUsecase;
import org.acme.features.market.place.application.usecase.ListPlaceUsecase;
import org.acme.features.market.place.application.usecase.RetrievePlaceUsecase;
import org.acme.features.market.place.application.usecase.UpdatePlaceUsecase;
import org.acme.features.market.place.domain.gateway.PlaceCursor;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.model.PlaceReference;
import org.acme.generated.openapi.api.PlaceApi;
import org.acme.generated.openapi.model.MerchantRef;
import org.acme.generated.openapi.model.Place;
import org.acme.generated.openapi.model.PlaceList;
import org.acme.generated.openapi.model.PlaceListNextOffset;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class PlaceController implements PlaceApi {

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final CreatePlaceUsecase create;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final DeletePlaceUsecase delete;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final ListPlaceUsecase list;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final RetrievePlaceUsecase retrieve;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final UpdatePlaceUsecase update;

  /**
   * @autogenerated ApiControllerGenerator
   * @param place
   * @return
   */
  @Override
  public Response placeApiCreate(Place place) {
    Actor actor = new Actor();
    Connection connection = new Connection();
    PlaceDto dto = toDomainModel(place);
    PlaceCreateResult result = create
        .create(PlaceCreateCommand.builder().actor(actor).connection(connection).dto(dto).build());
    try {
      Optional<PlaceDto> outputPlace =
          result.getPlace().toCompletableFuture().get(1, TimeUnit.SECONDS);
      return outputPlace.map(res -> Response.ok(toApiModel(res)).build())
          .orElseGet(() -> Response.status(404).build());
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response placeApiDelete(final String uid) {
    Actor actor = new Actor();
    Connection connection = new Connection();
    PlaceDeleteResult result = delete.delete(PlaceDeleteCommand.builder().actor(actor)
        .connection(connection).reference(PlaceReference.of(uid)).build());
    try {
      Optional<PlaceDto> place = result.getPlace().toCompletableFuture().get(1, TimeUnit.SECONDS);
      return place.map(res -> Response.ok(toApiModel(res)).build())
          .orElseGet(() -> Response.status(404).build());
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
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
  public Response placeApiList(final String uid, final List<String> uids, final String search,
      final String merchant, final Integer limit, final String sinceUid) {
    PlaceFilter.PlaceFilterBuilder filter = PlaceFilter.builder();
    PlaceCursor.PlaceCursorBuilder cursor = PlaceCursor.builder();
    cursor = cursor.limit(limit);
    cursor = cursor.sinceUid(sinceUid);
    filter = filter.uid(uid);
    filter = filter.uids(uids);
    filter = filter.search(search);
    if (null != merchant) {
      filter = filter.merchant(MerchantReference.of(merchant));
    }
    Actor actor = new Actor();
    Connection connection = new Connection();
    PlaceListResult result = list.list(PlaceListQuery.builder().actor(actor).connection(connection)
        .filter(filter.build()).cursor(cursor.build()).build());
    try {
      List<PlaceDto> places = result.getPlaces().toCompletableFuture().get(1, TimeUnit.SECONDS);
      PlaceList res = new PlaceList();
      res.setContent(toApiModel(places));
      res.setNext(next(places));
      return Response.ok(res).build();
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response placeApiRetrieve(final String uid) {
    Actor actor = new Actor();
    Connection connection = new Connection();
    PlaceRetrieveResult result = retrieve.retrieve(PlaceRetrieveQuery.builder().actor(actor)
        .connection(connection).reference(PlaceReference.of(uid)).build());
    try {
      Optional<PlaceDto> place = result.getPlace().toCompletableFuture().get(1, TimeUnit.SECONDS);
      return place.map(res -> Response.ok(toApiModel(res)).build())
          .orElseGet(() -> Response.status(404).build());
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response placeApiRetrievePhoto(final String uid) {
    return null;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param temp
   * @return
   */
  @Override
  public Response placeApiRetrieveTempUploadPhoto(final String temp) {
    return null;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param place
   * @return
   */
  @Override
  public Response placeApiUpdate(final String uid, final Place place) {
    Actor actor = new Actor();
    Connection connection = new Connection();
    PlaceDto dto = toDomainModel(place);
    PlaceUpdateResult result = update.update(PlaceUpdateCommand.builder().actor(actor)
        .connection(connection).dto(dto).reference(PlaceReference.of(uid)).build());
    try {
      Optional<PlaceDto> outputPlace =
          result.getPlace().toCompletableFuture().get(1, TimeUnit.SECONDS);
      return outputPlace.map(res -> Response.ok(toApiModel(res)).build())
          .orElseGet(() -> Response.status(404).build());
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      e.printStackTrace();
      return Response.serverError().build();
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param file
   * @return
   */
  @Override
  public Response placeApiUploadTempUploadPhoto(final InputStream file) {
    return null;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param list
   * @return
   */
  private PlaceListNextOffset next(List<PlaceDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      PlaceListNextOffset next = new PlaceListNextOffset();
      PlaceDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid());
      return next;
    }
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param places
   * @return
   */
  private List<Place> toApiModel(List<PlaceDto> places) {
    return places.stream().map(this::toApiModel).toList();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param dto
   * @return
   */
  private Place toApiModel(PlaceDto dto) {
    Place place = new Place();
    place.setUid(dto.getUid());
    place.setName(dto.getName());
    place.setMerchant(new MerchantRef().$ref(dto.getMerchant()));
    place.setPhoto(dto.getPhoto());
    place.setOpeningDate(dto.getOpeningDate());
    place.setVersion(dto.getVersion());
    return place;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param place
   * @return
   */
  private PlaceDto toDomainModel(Place place) {
    return PlaceDto.builder().uid(place.getUid()).name(place.getName())
        .merchant(Optional.ofNullable(place.getMerchant()).map(MerchantRef::get$Ref).orElse(null))
        .photo(place.getPhoto()).openingDate(place.getOpeningDate()).version(place.getVersion())
        .build();
  }
}
