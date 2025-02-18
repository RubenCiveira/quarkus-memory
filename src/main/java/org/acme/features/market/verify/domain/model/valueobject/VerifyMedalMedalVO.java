package org.acme.features.market.verify.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.acme.common.validation.AbstractFailList;
import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;
import org.acme.features.market.medal.domain.model.MedalRef;
import org.acme.features.market.medal.domain.model.MedalReference;

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
public class VerifyMedalMedalVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param medal
   * @return An empty instance
   */
  public static VerifyMedalMedalVO from(final MedalRef medal) {
    return tryFrom(medal);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param medalUid
   * @return An empty instance
   */
  public static VerifyMedalMedalVO fromReference(final String medalUid) {
    return from(MedalReference.of(medalUid));
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param medal
   * @return An empty instance
   */
  public static VerifyMedalMedalVO tryFrom(final Object medal) {
    ConstraintFailList list = new ConstraintFailList();
    VerifyMedalMedalVO result = tryFrom(medal, list);
    if (list.hasErrors()) {
      throw new ConstraintException(list);
    }
    return result;
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param medal temptative value
   * @param fails Error list
   * @return An empty instance
   */
  public static <T extends AbstractFailList> VerifyMedalMedalVO tryFrom(final Object medal,
      final T fails) {
    if (null == medal) {
      fails.add(new ConstraintFail("not-null", "medal", null, "Cant be null"));
      return null;
    } else if (medal instanceof MedalRef) {
      return new VerifyMedalMedalVO((MedalRef) medal);
    } else {
      fails.add(new ConstraintFail("wrong-type", "medal", medal.getClass(),
          "A MedalRef type is expected for medal"));
      return null;
    }
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param medalUid
   * @return An empty instance
   */
  public static VerifyMedalMedalVO tryFromReference(final String medalUid) {
    return tryFrom(MedalReference.of(medalUid));
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param medalUid
   * @param fails Error list
   * @return An empty instance
   */
  public static VerifyMedalMedalVO tryFromReference(final String medalUid,
      final ConstraintFailList fails) {
    return tryFrom(MedalReference.of(medalUid), fails);
  }

  /**
   * El medal de verify medal
   *
   * @autogenerated ValueObjectGenerator
   */
  private final MedalRef value;

  /**
   * @autogenerated ValueObjectGenerator
   * @return
   */
  public String getReferenceValue() {
    return value.getUidValue();
  }
}
