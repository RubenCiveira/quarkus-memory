package org.acme.features.market.place.infrastructure.driver.rest;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.acme.common.infrastructure.CurrentRequest;
import org.acme.common.store.BinaryContent;
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
import org.apache.commons.lang3.StringUtils;

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
    PlaceDto created = create.create(
        PlaceCreateCommand.builder().dto(toDomainModel(place)).build(currentRequest.interaction()));
    return Response.ok(toApiModel(created)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  @Transactional
  public Response placeApiDelete(final String uid) {
    PlaceDto deleted = delete.delete(PlaceDeleteCommand.builder().reference(PlaceReference.of(uid))
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
  public Response placeApiList(final List<String> uids, final String search, final String name,
      final List<String> merchants, final Integer limit, final String sinceUid,
      final String sinceName, final String order) {
    List<PlaceOrder> orderSteps = null == order ? List.of()
        : Arrays.asList(order.split(",")).stream().map(this::mapOrder).filter(Objects::nonNull)
            .toList();
    PlaceFilter.PlaceFilterBuilder filterBuilder = PlaceFilter.builder();
    PlaceCursor.PlaceCursorBuilder cursorBuilder = PlaceCursor.builder();
    cursorBuilder = cursorBuilder.limit(limit);
    cursorBuilder = cursorBuilder.sinceUid(sinceUid);
    filterBuilder =
        filterBuilder.uids(uids.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    filterBuilder = filterBuilder.search(search);
    filterBuilder = filterBuilder.name(name);
    filterBuilder = filterBuilder
        .merchants(merchants.stream().flatMap(part -> Stream.of(part.split(","))).toList());
    cursorBuilder = cursorBuilder.sinceName(sinceName);
    cursorBuilder = cursorBuilder.order(orderSteps);
    PlaceFilter filter = filterBuilder.build();
    PlaceCursor cursor = cursorBuilder.build();
    List<PlaceDto> listed = list.list(
        PlaceListQuery.builder().filter(filter).cursor(cursor).build(currentRequest.interaction()));
    /* .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME)) */
    return Response.ok(toListApiModel(listed, filter, cursor)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response placeApiRetrieve(final String uid) {
    PlaceDto retrieved = retrieve.retrieve(PlaceRetrieveQuery.builder()
        .reference(PlaceReference.of(uid)).build(currentRequest.interaction()));
    /* .header("Last-Modified", value.getSince().format(DateTimeFormatter.RFC_1123_DATE_TIME))) */
    return Response.ok(toApiModel(retrieved)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param uid
   * @return
   */
  @Override
  public Response placeApiRetrievePhoto(final String uid) {
    PlaceRetrieveUploadPhotoQuery query = PlaceRetrieveUploadPhotoQuery.builder()
        .reference(PlaceReference.of(uid)).build(currentRequest.interaction());
    BinaryContent stream = retrievePhotoUploadUsecase.read(query);
    return Response.ok(stream.getInputStream()).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param temp
   * @return
   */
  @Override
  public Response placeApiRetrieveTempUploadPhoto(final String temp) {
    PlacePhotoTemporalUploadReadQuery query =
        PlacePhotoTemporalUploadReadQuery.builder().key(temp).build(currentRequest.interaction());
    BinaryContent stream = tempPhotoUploadUsecase.read(query);
    return Response.ok(stream.getInputStream()).build();
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
    PlaceDto updated = update.update(PlaceUpdateCommand.builder().dto(toDomainModel(place))
        .reference(PlaceReference.of(uid)).build(currentRequest.interaction()));
    return Response.ok(toApiModel(updated)).build();
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param file
   * @return
   */
  @Override
  public Response placeApiUploadTempUploadPhoto(final InputStream file) {
    PlacePhotoTemporalUploadCommand command = PlacePhotoTemporalUploadCommand.builder()
        .binary(BinaryContent.builder().name("photo").contentType("")
            .lastModification(System.currentTimeMillis()).inputStream(file).build())
        .build(currentRequest.interaction());
    String key = tempPhotoUploadUsecase.upload(command);
    return Response
        .ok(currentRequest.getPublicHost() + "/api/market/places/-/temp-photo?temp=" + key).build();
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
    if (!StringUtils.isBlank(place.getPhoto())) {
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

  /**
   * @autogenerated ApiControllerGenerator
   * @param places
   * @param filter
   * @param cursor
   * @return
   */
  private PlaceList toListApiModel(List<PlaceDto> places, PlaceFilter filter, PlaceCursor cursor) {
    Optional<PlaceDto> last =
        places.isEmpty() ? Optional.empty() : Optional.of(places.get(places.size() - 1));
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
    return new PlaceList().items(places.stream().map(this::toApiModel).toList())
        .next(next.length() > 1 ? "?" + next.substring(1) : "")
        .self(self.length() > 1 ? "?" + self.substring(1) : "")
        .first(first.length() > 1 ? "?" + first.substring(1) : "");
  }

  /**
   * @autogenerated ApiControllerGenerator
   * @param order
   * @return
   */
  private String writeOrder(final PlaceOrder order) {
    if (null == order) {
      return null;
    } else if (order == PlaceOrder.NAME_ASC) {
      return "name-asc";
    } else if (order == PlaceOrder.NAME_DESC) {
      return "name-desc";
    } else {
      return null;
    }
  }
}
