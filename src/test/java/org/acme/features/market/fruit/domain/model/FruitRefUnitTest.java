package org.acme.features.market.fruit.domain.model;

import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitRefUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_fruit_ref_builder() {
    FruitRef ref = FruitRef.builder().uid(FruitUidVO.from("one")).build();
    Assertions.assertEquals("one", ref.getUid().getValue());
  }
}
