package org.acme.features.market.place.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@Getter
@ToString
@RequiredArgsConstructor
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PlaceUidVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param uid
   * @return An empty instance
   */
  public static PlaceUidVO from(final String uid) {
    return tryFrom(uid);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param uid
   * @return An empty instance
   */
  public static PlaceUidVO tryFrom(final Object uid) {
    ConstraintFailList list = new ConstraintFailList();
    PlaceUidVO result = tryFrom(uid, list);
    if (list.hasErrors()) {
      throw new ConstraintException(list);
    }
    return result;
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param uid temptative value
   * @param fails Error list
   * @return An empty instance
   */
  public static PlaceUidVO tryFrom(final Object uid, final ConstraintFailList fails) {
    if (null == uid) {
      fails.add(new ConstraintFail("not-null", "uid", null, "Cant be null"));
      return null;
    } else if (uid instanceof String) {
      return new PlaceUidVO((String) uid);
    } else {
      fails.add(new ConstraintFail("wrong-type", "uid", uid.getClass(),
          "A String type is expected for uid"));
      return null;
    }
  }

  /**
   * A number to identify the db record
   *
   * @autogenerated ValueObjectGenerator
   */
  private final String value;

  /**
   * @autogenerated ValueObjectGenerator
   * @return
   */
  public String getValue() {
    return value;
  }
}