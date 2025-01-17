package org.acme.features.market.area.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AreaPlaceVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property place of area ")
  void test_area_place_v_o_builder() {
    Assertions.assertThrows(ConstraintException.class, () -> AreaPlaceVO.tryFrom(null));
    Assertions.assertEquals(null, AreaPlaceVO.from(null).getValue());
    Assertions.assertThrows(ConstraintException.class, () -> AreaPlaceVO.tryFrom(new Object() {}));
  }
}
