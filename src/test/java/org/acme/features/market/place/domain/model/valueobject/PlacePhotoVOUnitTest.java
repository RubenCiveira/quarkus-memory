package org.acme.features.market.place.domain.model.valueobject;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlacePhotoVOUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test value object contruction for property photo of place ")
  void test_place_photo_v_o_builder() {
    Assertions.assertFalse(PlacePhotoVO.empty().getValue().isPresent(),
        "A empty vo should have a null value");
    Assertions.assertFalse(PlacePhotoVO.tryFrom(null).getValue().isPresent());
    Assertions.assertEquals(null, PlacePhotoVO.from(null).getValue().orElse(null));
    Assertions.assertThrows(ConstraintException.class, () -> PlacePhotoVO.tryFrom(new Object() {}));
  }
}
