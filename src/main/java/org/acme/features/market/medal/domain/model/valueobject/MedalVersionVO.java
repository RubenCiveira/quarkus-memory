package org.acme.features.market.medal.domain.model.valueobject;

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
public class MedalVersionVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @return An empty instance
   */
  public static MedalVersionVO empty() {
    return new MedalVersionVO(null);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param version
   * @return An empty instance
   */
  public static MedalVersionVO from(final Integer version) {
    return tryFrom(version);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param version
   * @return An empty instance
   */
  public static MedalVersionVO tryFrom(final Object version) {
    ConstraintFailList list = new ConstraintFailList();
    MedalVersionVO result = tryFrom(version, list);
    if (list.hasErrors()) {
      throw new ConstraintException(list);
    }
    return result;
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param version temptative value
   * @param fails Error list
   * @return An empty instance
   */
  public static <T extends AbstractFailList> MedalVersionVO tryFrom(final Object version,
      final T fails) {
    if (null == version) {
      return new MedalVersionVO(null);
    } else if (version instanceof Integer) {
      return new MedalVersionVO((Integer) version);
    } else {
      fails.add(new ConstraintFail("wrong-type", "version", version.getClass(),
          "A Integer type is expected for version"));
      return null;
    }
  }

  /**
   * Campo con el número de version de medal para controlar bloqueos optimistas
   *
   * @autogenerated ValueObjectGenerator
   */
  private final Integer value;

  /**
   * @autogenerated ValueObjectGenerator
   * @return
   */
  public Optional<Integer> getValue() {
    return Optional.ofNullable(value);
  }
}
