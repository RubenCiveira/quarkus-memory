package org.acme.features.market.place.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlaceReferenceUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test Place reference contruction")
  void test_place_reference_builder() {
    PlaceReference ref = PlaceReference.of("one");
    Assertions.assertEquals("one", ref.getUid().getValue());
  }
}
