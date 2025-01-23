package org.acme.features.market.place.infrastructure.driver.rest;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.acme.common.rest.CurrentRequest;
import org.acme.common.store.BinaryContent;
import org.acme.features.market.merchant.domain.model.MerchantReference;
import org.acme.features.market.place.application.PlaceDto;
import org.acme.features.market.place.application.usecase.create.CreatePlaceUsecase;
import org.acme.features.market.place.application.usecase.create.PlaceCreateCommand;
import org.acme.features.market.place.application.usecase.delete.DeletePlaceUsecase;
import org.acme.features.market.place.application.usecase.delete.PlaceDeleteCommand;
import org.acme.features.market.place.application.usecase.list.ListPlaceUsecase;
import org.acme.features.market.place.application.usecase.list.PlaceListQuery;
import org.acme.features.market.place.application.usecase.photo.retrieve.PlaceRetrieveUploadPhotoQuery;
import org.acme.features.market.place.application.usecase.photo.retrieve.RetrievePhotoUploadUsecase;
import org.acme.features.market.place.application.usecase.photo.upload.PhotoTemporalUploadUsecase;
import org.acme.features.market.place.application.usecase.photo.upload.PlacePhotoTemporalUploadCommand;
import org.acme.features.market.place.application.usecase.photo.upload.PlacePhotoTemporalUploadReadQuery;
import org.acme.features.market.place.application.usecase.retrieve.PlaceRetrieveQuery;
import org.acme.features.market.place.application.usecase.retrieve.RetrievePlaceUsecase;
import org.acme.features.market.place.application.usecase.update.PlaceUpdateCommand;
import org.acme.features.market.place.application.usecase.update.UpdatePlaceUsecase;
import org.acme.features.market.place.domain.gateway.PlaceCursor;
import org.acme.features.market.place.domain.gateway.PlaceFilter;
import org.acme.features.market.place.domain.gateway.PlaceOrder;
import org.acme.features.market.place.domain.model.PlaceReference;
import org.acme.features.market.place.domain.model.valueobject.PlaceMerchantVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceNameVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceOpeningDateVO;
import org.acme.features.market.place.domain.model.valueobject.PlacePhotoVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceUidVO;
import org.acme.features.market.place.domain.model.valueobject.PlaceVersionVO;
import org.acme.generated.openapi.api.PlaceApi;
import org.acme.generated.openapi.model.MerchantRef;
import org.acme.generated.openapi.model.Place;
import org.acme.generated.openapi.model.PlaceList;
import org.acme.generated.openapi.model.PlaceListNextOffset;

import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
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
   * Place
   *
   * @autogenerated ApiControllerGenerator
   */
  private final CurrentRequest currentRequest;

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
  private final RetrievePhotoUploadUsecase retrievePhotoUploadUsecase;

  /**
   * @autogenerated ApiControllerGenerator
   */
  private final PhotoTemporalUploadUsecase tempPhotoUploadUsecase;

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
  @Transactional
  public Response placeApiCreate(Place place) {
    return currentRequest.resolve(interaction -> create
        .create(PlaceCreateCommand.builder().dto(toDomainModel(place)).build(interaction))
        .thenApply(res -> res.getPlace().map(this::toApiModel)));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  @Transactional
  public Response placeApiDelete(final String uid) {
    return currentRequest.resolve(interaction -> delete
        .delete(PlaceDeleteCommand.builder().reference(PlaceReference.of(uid)).build(interaction))
        .thenApply(res -> res.getPlace().map(this::toApiModel)));
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
  public Response placeApiList(final String uid, final List<String> uids, final String search,
      final String name, final String merchant, final Integer limit, final String sinceUid,
      final String sinceName, final String order) {
    List<PlaceOrder> orderSteps = null == order ? List.of()
        : Arrays.asList(order.split(",")).stream().map(this::mapOrder).filter(Objects::nonNull)
            .toList();
    return currentRequest.resolve(interaction -> {
      PlaceFilter.PlaceFilterBuilder filter = PlaceFilter.builder();
      PlaceCursor.PlaceCursorBuilder cursor = PlaceCursor.builder();
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
      return list.list(PlaceListQuery.builder().filter(filter.build()).cursor(cursor.build())
          .build(interaction));
    }, value -> Response
        .ok(new PlaceList().content(toApiModel(value.getPlaces()))
            .next(next(orderSteps, value.getPlaces())))
        .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME))
        .build());
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response placeApiRetrieve(final String uid) {
    return currentRequest.resolve(
        interaction -> retrieve.retrieve(
            PlaceRetrieveQuery.builder().reference(PlaceReference.of(uid)).build(interaction)),
        value -> value.getPlace()
            .map(place -> Response.ok(toApiModel(place)).header("Last-Modified",
                value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME)))
            .orElseGet(() -> Response.status(404)).build());
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response placeApiRetrievePhoto(final String uid) {
    return currentRequest.resolve(interaction -> {
      PlaceRetrieveUploadPhotoQuery query = PlaceRetrieveUploadPhotoQuery.builder()
          .reference(PlaceReference.of(uid)).build(interaction);
      return retrievePhotoUploadUsecase.read(query)
          .thenApply(op -> op.getPhoto().map(BinaryContent::getInputStream));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param temp
   * @return
   */
  @Override
  public Response placeApiRetrieveTempUploadPhoto(final String temp) {
    return currentRequest.resolve(interaction -> {
      PlacePhotoTemporalUploadReadQuery query =
          PlacePhotoTemporalUploadReadQuery.builder().key(temp).build(interaction);
      return tempPhotoUploadUsecase.read(query)
          .thenApply(op -> op.getBinary().map(BinaryContent::getInputStream));
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @param place
   * @return
   */
  @Override
  @Transactional
  public Response placeApiUpdate(final String uid, final Place place) {
    return currentRequest.resolve(interaction -> update
        .update(PlaceUpdateCommand.builder().dto(toDomainModel(place))
            .reference(PlaceReference.of(uid)).build(interaction))
        .thenApply(res -> res.getPlace().map(this::toApiModel)));
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param file
   * @return
   */
  @Override
  public Response placeApiUploadTempUploadPhoto(final InputStream file) {
    return currentRequest.resolve(interaction -> {
      PlacePhotoTemporalUploadCommand command = PlacePhotoTemporalUploadCommand.builder()
          .binary(BinaryContent.builder().name("photo").contentType("")
              .lastModification(System.currentTimeMillis()).inputStream(file).build())
          .build(interaction);
      return tempPhotoUploadUsecase.upload(command).thenApply(key -> currentRequest.getPublicHost()
          + "/api/market/places/-/temp-photo?temp=" + key.getKey());
    });
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param label
   * @return
   */
  private PlaceOrder mapOrder(final String label) {
    if (null == label) {
      return null;
    } else if (label.trim().equals("name-asc")) {
      return PlaceOrder.NAME_ASC;
    } else if (label.trim().equals("name-desc")) {
      return PlaceOrder.NAME_DESC;
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
  private PlaceListNextOffset next(List<PlaceOrder> order, List<PlaceDto> list) {
    if (list.isEmpty()) {
      return null;
    } else {
      PlaceListNextOffset next = new PlaceListNextOffset();
      PlaceDto last = list.get(list.size() - 1);
      next.setSinceUid(last.getUid().getValue());
      if (order.contains(PlaceOrder.NAME_ASC)) {
        next.setSinceName(last.getName().getValue());
      }
      if (order.contains(PlaceOrder.NAME_DESC)) {
        next.setSinceName(last.getName().getValue());
      }
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
    place.setUid(Optional.ofNullable(dto.getUid()).map(PlaceUidVO::getValue).orElse(null));
    place.setName(Optional.ofNullable(dto.getName()).map(PlaceNameVO::getValue).orElse(null));
    place.setMerchant(new MerchantRef().$ref(Optional.ofNullable(dto.getMerchant())
        .map(PlaceMerchantVO::getReferenceValue).orElse(null)));
    String photo = Optional.ofNullable(dto.getPhoto()).flatMap(PlacePhotoVO::getValue).orElse(null);
    if (null != photo) {
      place.setPhoto(currentRequest.getPublicHost() + "/api/market/places/"
          + dto.getUid().getValue() + "/photo");
    }
    place.setOpeningDate(Optional.ofNullable(dto.getOpeningDate())
        .flatMap(PlaceOpeningDateVO::getValue).orElse(null));
    place.setVersion(
        Optional.ofNullable(dto.getVersion()).flatMap(PlaceVersionVO::getValue).orElse(null));
    return place;
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param place
   * @return
   */
  private PlaceDto toDomainModel(Place place) {
    PlaceDto.PlaceDtoBuilder builder = PlaceDto.builder();
    if (null != place.getUid()) {
      builder = builder.uid(PlaceUidVO.from(place.getUid()));
    }
    if (null != place.getName()) {
      builder = builder.name(PlaceNameVO.from(place.getName()));
    }
    if (null != place.getMerchant()) {
      builder = builder.merchant(PlaceMerchantVO.fromReference(
          Optional.ofNullable(place.getMerchant()).map(MerchantRef::get$Ref).orElse(null)));
    }
    if (place.getPhoto() != null) {
      String url = place.getPhoto();
      if (place.getPhoto()
          .startsWith(currentRequest.getPublicHost() + "/api/market/places/-/temp-photo?temp=")) {
        builder = builder.photo(PlacePhotoVO.fromTemporal(url.substring(
            (currentRequest.getPublicHost() + "/api/market/places/-/temp-photo?temp=").length())));
      } else if (!(url.equals(
          currentRequest.getPublicHost() + "/api/market/places/" + place.getUid() + "/photo"))) {
        builder = builder.photo(PlacePhotoVO.from(place.getPhoto()));
      }
    }
    if (null != place.getOpeningDate()) {
      builder = builder.openingDate(PlaceOpeningDateVO.from(place.getOpeningDate()));
    }
    if (null != place.getVersion()) {
      builder = builder.version(PlaceVersionVO.from(place.getVersion()));
    }
    return builder.build();
  }
}
