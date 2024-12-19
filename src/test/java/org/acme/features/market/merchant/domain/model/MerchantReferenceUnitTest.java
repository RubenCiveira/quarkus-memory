package org.acme.features.market.merchant.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MerchantReferenceUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_merchant_reference_builder() {
    MerchantReference ref = MerchantReference.of("one");
    Assertions.assertEquals("one", ref.getUid().getValue());
  }
}