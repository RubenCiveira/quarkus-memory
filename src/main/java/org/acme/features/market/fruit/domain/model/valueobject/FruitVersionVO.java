package org.acme.features.market.fruit.domain.model.valueobject;

import java.util.Optional;

import org.acme.common.exception.ConstraintException;
import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitVersionVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @return An empty instance
   */
  public static FruitVersionVO empty() {
    return new FruitVersionVO(null);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param version
   * @return An empty instance
   */
  public static FruitVersionVO from(final Integer version) {
    return tryFrom(version);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param version
   * @return An empty instance
   */
  public static FruitVersionVO tryFrom(final Object version) {
    ConstraintFailList list = new ConstraintFailList();
    FruitVersionVO result = tryFrom(version, list);
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
  public static FruitVersionVO tryFrom(final Object version, final ConstraintFailList fails) {
    if (null == version) {
      return new FruitVersionVO(null);
    } else if (version instanceof Integer) {
      return new FruitVersionVO((Integer) version);
    } else {
      fails.add(new ConstraintFail("wrong-type", "version", version.getClass(),
          "A Integer type is expected for version"));
      return null;
    }
  }

  /**
   * Campo con el número de version de fruit para controlar bloqueos optimistas
   *
   * @autogenerated ValueObjectGenerator
   */
  private final Integer version;

  /**
   * @autogenerated ValueObjectGenerator
   * @return
   */
  public Optional<Integer> getValue() {
    return Optional.ofNullable(version);
  }
}