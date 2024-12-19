package org.acme.features.market.place.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaceMerchantVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property merchant of place ")
  void test_place_merchant_v_o_builder() {
    Assertions.assertThrows(ConstraintException.class, () -> PlaceMerchantVO.tryFrom(null));
    Assertions.assertEquals(null, PlaceMerchantVO.from(null).getValue());
    Assertions.assertThrows(ConstraintException.class,
        () -> PlaceMerchantVO.tryFrom(new Object() {}));
  }
}