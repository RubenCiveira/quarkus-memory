package org.acme.features.market.color.application.interaction;

import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.valueobject.ColorMerchantVO;
import org.acme.features.market.color.domain.model.valueobject.ColorNameVO;
import org.acme.features.market.color.domain.model.valueobject.ColorUidVO;
import org.acme.features.market.color.domain.model.valueobject.ColorVersionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColorDtoUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_color_dto_builder() {
    Color entity = Color.builder().uid(ColorUidVO.from("one")).name(ColorNameVO.from("one"))
        .merchant(ColorMerchantVO.from(null)).version(ColorVersionVO.from(1)).build();
    Color fixRef = Color.builder().uid(ColorUidVO.from("two")).name(ColorNameVO.from("two"))
        .merchant(ColorMerchantVO.from(null)).version(ColorVersionVO.from(2)).build();
    Color other;
    ColorDto dto = ColorDto.from(entity);
    Assertions.assertEquals("one", dto.getUid());
    Assertions.assertEquals("one", dto.getName());
    Assertions.assertEquals(null, dto.getMerchant());
    Assertions.assertEquals(1, dto.getVersion());
    other = dto.toEntityBuilder().build();
    dto.hide("-");
    dto.fix("-", fixRef);
    Assertions.assertEquals("one", other.getUid().getValue());
    Assertions.assertEquals("one", other.getName().getValue());
    Assertions.assertEquals(null, other.getMerchant().getValue().orElse(null));
    Assertions.assertEquals(1, other.getVersion().getValue().orElse(null));
    dto.fix("uid", fixRef);
    dto.fix("name", fixRef);
    dto.fix("merchant", fixRef);
    dto.fix("version", fixRef);
    other = dto.toEntityBuilder().build();
    Assertions.assertEquals("two", other.getUid().getValue());
    Assertions.assertEquals("two", other.getName().getValue());
    Assertions.assertEquals(null, other.getMerchant().getValue().orElse(null));
    Assertions.assertEquals(2, other.getVersion().getValue().orElse(null));
    dto.hide("uid");
    dto.hide("name");
    dto.hide("merchant");
    dto.hide("version");
    Assertions.assertNull(dto.getUid());
    Assertions.assertNull(dto.getName());
    Assertions.assertNull(dto.getMerchant());
    Assertions.assertNull(dto.getVersion());
    dto = ColorDto.from(entity);
    Assertions.assertEquals("one", dto.getUid());
    Assertions.assertEquals("one", dto.getName());
    Assertions.assertEquals(null, dto.getMerchant());
    Assertions.assertEquals(1, dto.getVersion());
    dto.fix("uid");
    dto.fix("name");
    dto.fix("merchant");
    dto.fix("version");
    Assertions.assertNull(dto.getUid());
    Assertions.assertNull(dto.getName());
    Assertions.assertNull(dto.getMerchant());
    Assertions.assertNull(dto.getVersion());
  }
}
