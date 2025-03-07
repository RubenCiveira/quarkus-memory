package org.acme.features.market.merchant.domain.model.valueobject;

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
public class MerchantVersionVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @return An empty instance
   */
  public static MerchantVersionVO empty() {
    return new MerchantVersionVO(null);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param version
   * @return An empty instance
   */
  public static MerchantVersionVO from(final Integer version) {
    return tryFrom(version);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param version
   * @return An empty instance
   */
  public static MerchantVersionVO tryFrom(final Object version) {
    ConstraintFailList list = new ConstraintFailList();
    MerchantVersionVO result = tryFrom(version, list);
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
  public static <T extends AbstractFailList> MerchantVersionVO tryFrom(final Object version,
      final T fails) {
    if (null == version) {
      return new MerchantVersionVO(null);
    } else if (version instanceof Integer) {
      return new MerchantVersionVO((Integer) version);
    } else {
      fails.add(new ConstraintFail("wrong-type", "version", version.getClass(),
          "A Integer type is expected for version"));
      return null;
    }
  }

  /**
   * Campo con el número de version de merchant para controlar bloqueos optimistas
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
