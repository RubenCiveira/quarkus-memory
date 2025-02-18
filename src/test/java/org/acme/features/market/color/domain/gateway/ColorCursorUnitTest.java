package org.acme.features.market.color.domain.gateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColorCursorUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_color_cursor_builder() {
    ColorCursor cursor = ColorCursor.builder().build();
    Assertions.assertFalse(cursor.getLimit().isPresent(),
        "Limit must be empty if cursor is build without it");
    Assertions.assertFalse(cursor.getSinceUid().isPresent(),
        "Since must be empty if cursor is build without it");
    cursor.setLimit(100);
    Assertions.assertEquals(100, cursor.getLimit().get(), "Limit must be the same as the assigned");
    cursor.setSinceUid("one");
    Assertions.assertEquals("one", cursor.getSinceUid().get(),
        "Since Uid must be the same as the assigned");
  }
}
