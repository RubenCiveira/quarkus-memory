package org.acme.features.market.place.domain.model.valueobject;

import java.time.OffsetDateTime;
import java.util.Optional;

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
public class PlaceOpeningDateVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @return An empty instance
   */
  public static PlaceOpeningDateVO empty() {
    return new PlaceOpeningDateVO(null);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param openingDate
   * @return An empty instance
   */
  public static PlaceOpeningDateVO from(final OffsetDateTime openingDate) {
    return tryFrom(openingDate);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param openingDate
   * @return An empty instance
   */
  public static PlaceOpeningDateVO tryFrom(final Object openingDate) {
    ConstraintFailList list = new ConstraintFailList();
    PlaceOpeningDateVO result = tryFrom(openingDate, list);
    if (list.hasErrors()) {
      throw new ConstraintException(list);
    }
    return result;
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param openingDate temptative value
   * @param fails Error list
   * @return An empty instance
   */
  public static <T extends AbstractFailList> PlaceOpeningDateVO tryFrom(final Object openingDate,
      final T fails) {
    if (null == openingDate) {
      return new PlaceOpeningDateVO(null);
    } else if (openingDate instanceof OffsetDateTime) {
      return new PlaceOpeningDateVO((OffsetDateTime) openingDate);
    } else {
      fails.add(new ConstraintFail("wrong-type", "openingDate", openingDate.getClass(),
          "A OffsetDateTime type is expected for openingDate"));
      return null;
    }
  }

  /**
   * El opening date de place
   *
   * @autogenerated ValueObjectGenerator
   */
  private final OffsetDateTime value;

  /**
   * @autogenerated ValueObjectGenerator
   * @return
   */
  public Optional<OffsetDateTime> getValue() {
    return Optional.ofNullable(value);
  }
}
