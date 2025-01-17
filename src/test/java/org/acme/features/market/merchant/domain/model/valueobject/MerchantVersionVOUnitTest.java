package org.acme.features.market.merchant.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MerchantVersionVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property version of merchant ")
  void test_merchant_version_v_o_builder() {
    Assertions.assertFalse(MerchantVersionVO.empty().getValue().isPresent(),
        "A empty vo should have a null value");
    Assertions.assertFalse(MerchantVersionVO.tryFrom(null).getValue().isPresent());
    Assertions.assertEquals(1, MerchantVersionVO.from(1).getValue().orElse(null));
    Assertions.assertThrows(ConstraintException.class,
        () -> MerchantVersionVO.tryFrom(new Object() {}));
  }
}
