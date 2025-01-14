package org.acme.features.market.area.domain.gateway;

import java.util.List;
import java.util.Optional;

import org.acme.features.market.place.domain.model.PlaceRef;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AreaFilter {

  /**
   * @autogenerated FilterGenerator
   */
  private PlaceRef place;

  /**
   * @autogenerated FilterGenerator
   */
  private String placeMerchantMerchantAccesible;

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated FilterGenerator
   */
  private String search;

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated FilterGenerator
   */
  private String uid;

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated FilterGenerator
   */
  private List<String> uids;

  /**
   * @autogenerated FilterGenerator
   * @return
   */
  public Optional<PlaceRef> getPlace() {
    return Optional.ofNullable(place);
  }

  /**
   * @autogenerated FilterGenerator
   * @return
   */
  public Optional<String> getPlaceMerchantMerchantAccesible() {
    return Optional.ofNullable(placeMerchantMerchantAccesible);
  }

  /**
   * @autogenerated FilterGenerator
   * @return
   */
  public Optional<String> getSearch() {
    return Optional.ofNullable(search);
  }

  /**
   * @autogenerated FilterGenerator
   * @return
   */
  public Optional<String> getUid() {
    return Optional.ofNullable(uid);
  }

  /**
   * @autogenerated FilterGenerator
   * @return
   */
  public List<String> getUids() {
    return null == uids ? List.of() : uids;
  }
}
