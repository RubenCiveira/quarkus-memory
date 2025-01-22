package org.acme.features.market.place.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.acme.common.validation.AbstractFailList;
import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;
import org.acme.features.market.merchant.domain.model.MerchantRef;
import org.acme.features.market.merchant.domain.model.MerchantReference;

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
public class PlaceMerchantVO {

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param merchant
   * @return An empty instance
   */
  public static PlaceMerchantVO from(final MerchantRef merchant) {
    return tryFrom(merchant);
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param merchantUid
   * @return An empty instance
   */
  public static PlaceMerchantVO fromReference(final String merchantUid) {
    return from(MerchantReference.of(merchantUid));
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param merchant
   * @return An empty instance
   */
  public static PlaceMerchantVO tryFrom(final Object merchant) {
    ConstraintFailList list = new ConstraintFailList();
    PlaceMerchantVO result = tryFrom(merchant, list);
    if (list.hasErrors()) {
      throw new ConstraintException(list);
    }
    return result;
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param merchant temptative value
   * @param fails Error list
   * @return An empty instance
   */
  public static <T extends AbstractFailList> PlaceMerchantVO tryFrom(final Object merchant,
      final T fails) {
    if (null == merchant) {
      fails.add(new ConstraintFail("not-null", "merchant", null, "Cant be null"));
      return null;
    } else if (merchant instanceof MerchantRef) {
      return new PlaceMerchantVO((MerchantRef) merchant);
    } else {
      fails.add(new ConstraintFail("wrong-type", "merchant", merchant.getClass(),
          "A MerchantRef type is expected for merchant"));
      return null;
    }
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param merchantUid
   * @return An empty instance
   */
  public static PlaceMerchantVO tryFromReference(final String merchantUid) {
    return tryFrom(MerchantReference.of(merchantUid));
  }

  /**
   * An empty instance
   *
   * @autogenerated ValueObjectGenerator
   * @param merchantUid
   * @param fails Error list
   * @return An empty instance
   */
  public static PlaceMerchantVO tryFromReference(final String merchantUid,
      final ConstraintFailList fails) {
    return tryFrom(MerchantReference.of(merchantUid), fails);
  }

  /**
   * El merchant de place
   *
   * @autogenerated ValueObjectGenerator
   */
  private final MerchantRef value;

  /**
   * @autogenerated ValueObjectGenerator
   * @return
   */
  public String getReferenceValue() {
    return value.getUidValue();
  }

  /**
   * @autogenerated ValueObjectGenerator
   * @return
   */
  public MerchantRef getValue() {
    return value;
  }
}
