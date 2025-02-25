package org.acme.features.market.color.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.acme.common.validation.AbstractFailList;
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
public class ColorUidVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param uid
   * @return An empty instance
   */
  public static ColorUidVO from(final String uid) {
    return tryFrom(uid);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param uid
   * @return An empty instance
   */
  public static ColorUidVO tryFrom(final Object uid) {
    ConstraintFailList list = new ConstraintFailList();
    ColorUidVO result = tryFrom(uid, list);
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
  public static <T extends AbstractFailList> ColorUidVO tryFrom(final Object uid, final T fails) {
    if (null == uid) {
      fails.add(new ConstraintFail("not-null", "uid", null, "Cant be null"));
      return null;
    } else if (uid instanceof String) {
      return new ColorUidVO((String) uid);
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
}
