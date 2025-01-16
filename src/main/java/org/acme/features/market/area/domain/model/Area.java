package org.acme.features.market.area.domain.model;

import java.util.Optional;

import org.acme.common.exception.ConstraintException;
import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;
import org.acme.features.market.area.domain.model.valueobject.AreaNameVO;
import org.acme.features.market.area.domain.model.valueobject.AreaPlaceVO;
import org.acme.features.market.area.domain.model.valueobject.AreaUidVO;
import org.acme.features.market.area.domain.model.valueobject.AreaVersionVO;
import org.acme.features.market.place.domain.model.PlaceRef;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;

@Getter
@ToString
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Area implements AreaRef {

  /**
   * @autogenerated EntityGenerator
   */
  public static class AreaBuilder {

    /**
     * @autogenerated EntityGenerator
     * @return
     */
    public Area buildValid() {
      ConstraintFailList list = new ConstraintFailList();
      if (null == uid) {
        list.add(new ConstraintFail("REQUIRED", "uid", null));
      }
      if (null == name) {
        list.add(new ConstraintFail("REQUIRED", "name", null));
      }
      if (null == place) {
        list.add(new ConstraintFail("REQUIRED", "place", null));
      }
      if (list.hasErrors()) {
        throw new ConstraintException(list);
      }
      return build();
    }

    /**
     * @autogenerated EntityGenerator
     * @param name
     * @return
     */
    public AreaBuilder nameValue(final String name) {
      return name(AreaNameVO.from(name));
    }

    /**
     * @autogenerated EntityGenerator
     * @param place
     * @return
     */
    public AreaBuilder placeReferenceValue(final String place) {
      return place(AreaPlaceVO.fromReference(place));
    }

    /**
     * @autogenerated EntityGenerator
     * @param place
     * @return
     */
    public AreaBuilder placeValue(final PlaceRef place) {
      return place(AreaPlaceVO.from(place));
    }

    /**
     * @autogenerated EntityGenerator
     * @param uid
     * @return
     */
    public AreaBuilder uidValue(final String uid) {
      return uid(AreaUidVO.from(uid));
    }

    /**
     * @autogenerated EntityGenerator
     * @param version
     * @return
     */
    public AreaBuilder versionValue(final Integer version) {
      return version(AreaVersionVO.from(version));
    }
  }

  /**
   * El name de area
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private AreaNameVO name;

  /**
   * El place de area
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private AreaPlaceVO place;

  /**
   * A number to identify the db record
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private AreaUidVO uid;

  /**
   * Campo con el número de version de area para controlar bloqueos optimistas
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  @Builder.Default
  private AreaVersionVO version = AreaVersionVO.empty();

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String getNameValue() {
    return getName().getValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String getPlaceReferenceValue() {
    return getPlace().getReferenceValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public PlaceRef getPlaceValue() {
    return getPlace().getValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public String getUidValue() {
    return getUid().getValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<Integer> getVersionValue() {
    return getVersion().getValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Area withEmptyVersion() {
    return withVersion(AreaVersionVO.empty());
  }

  /**
   * @autogenerated EntityGenerator
   * @param name
   * @return
   */
  public Area withNameValue(final String name) {
    return withName(AreaNameVO.from(name));
  }

  /**
   * @autogenerated EntityGenerator
   * @param place
   * @return
   */
  public Area withPlaceReferenceValue(final String place) {
    return withPlace(AreaPlaceVO.fromReference(place));
  }

  /**
   * @autogenerated EntityGenerator
   * @param place
   * @return
   */
  public Area withPlaceValue(final PlaceRef place) {
    return withPlace(AreaPlaceVO.from(place));
  }

  /**
   * @autogenerated EntityGenerator
   * @param uid
   * @return
   */
  public Area withUidValue(final String uid) {
    return withUid(AreaUidVO.from(uid));
  }

  /**
   * @autogenerated EntityGenerator
   * @param version
   * @return
   */
  public Area withVersionValue(final Integer version) {
    return withVersion(AreaVersionVO.from(version));
  }

  /**
   * @autogenerated EntityGenerator
   * @param version
   * @return
   */
  public Area withVersionValue(final Optional<Integer> version) {
    return version.isPresent() ? withVersion(AreaVersionVO.from(version.get()))
        : withEmptyVersion();
  }
}