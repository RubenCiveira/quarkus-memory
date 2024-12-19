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
public class PlaceNameVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param name
   * @return An empty instance
   */
  public static PlaceNameVO from(final String name) {
    return tryFrom(name);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param name
   * @return An empty instance
   */
  public static PlaceNameVO tryFrom(final Object name) {
    ConstraintFailList list = new ConstraintFailList();
    PlaceNameVO result = tryFrom(name, list);
    if (list.hasErrors()) {
      throw new ConstraintException(list);
    }
    return result;
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param name temptative value
   * @param fails Error list
   * @return An empty instance
   */
  public static PlaceNameVO tryFrom(final Object name, final ConstraintFailList fails) {
    if (null == name) {
      fails.add(new ConstraintFail("not-null", "name", null, "Cant be null"));
      return null;
    } else if (name instanceof String) {
      return new PlaceNameVO((String) name);
    } else {
      fails.add(new ConstraintFail("wrong-type", "name", name.getClass(),
          "A String type is expected for name"));
      return null;
    }
  }

  /**
   * El name de place
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