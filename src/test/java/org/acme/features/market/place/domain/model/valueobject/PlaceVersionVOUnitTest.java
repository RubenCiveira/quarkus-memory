package org.acme.features.market.place.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaceVersionVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property version of place ")
  void test_place_version_v_o_builder() {
    Assertions.assertFalse(PlaceVersionVO.empty().getValue().isPresent(),
        "A empty vo should have a null value");
    Assertions.assertFalse(PlaceVersionVO.tryFrom(null).getValue().isPresent());
    Assertions.assertEquals(1, PlaceVersionVO.from(1).getValue().orElse(null));
    Assertions.assertThrows(ConstraintException.class,
        () -> PlaceVersionVO.tryFrom(new Object() {}));
  }
}
