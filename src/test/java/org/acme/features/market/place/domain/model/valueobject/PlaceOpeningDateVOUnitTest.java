package org.acme.features.market.place.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaceOpeningDateVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property opening date of place ")
  void test_place_opening_date_v_o_builder() {
    Assertions.assertFalse(PlaceOpeningDateVO.empty().getValue().isPresent(),
        "A empty vo should have a null value");
    Assertions.assertFalse(PlaceOpeningDateVO.tryFrom(null).getValue().isPresent());
    Assertions.assertEquals(null, PlaceOpeningDateVO.from(null).getValue().orElse(null));
    Assertions.assertThrows(ConstraintException.class,
        () -> PlaceOpeningDateVO.tryFrom(new Object() {}));
  }
}
