package org.acme.features.market.color.application;

import java.util.Optional;

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
    other = dto.toEntityBuilder(Optional.empty()).build();
    dto.hideField("-");
    dto.fixField("-", fixRef);
    Assertions.assertEquals("one", other.getUid().getValue());
    Assertions.assertEquals("one", other.getName().getValue());
    Assertions.assertEquals(null, other.getMerchant().getValue().orElse(null));
    Assertions.assertEquals(1, other.getVersion().getValue().orElse(null));
    dto.fixField("uid", fixRef);
    dto.fixField("name", fixRef);
    dto.fixField("merchant", fixRef);
    dto.fixField("version", fixRef);
    other = dto.toEntityBuilder(Optional.empty()).build();
    Assertions.assertEquals("two", other.getUid().getValue());
    Assertions.assertEquals("two", other.getName().getValue());
    Assertions.assertEquals(null, other.getMerchant().getValue().orElse(null));
    Assertions.assertEquals(2, other.getVersion().getValue().orElse(null));
    dto.hideField("uid");
    dto.hideField("name");
    dto.hideField("merchant");
    dto.hideField("version");
    Assertions.assertNull(dto.getUid());
    Assertions.assertNull(dto.getName());
    Assertions.assertNull(dto.getMerchant());
    Assertions.assertNull(dto.getVersion());
    dto = ColorDto.from(entity);
    Assertions.assertEquals("one", dto.getUid());
    Assertions.assertEquals("one", dto.getName());
    Assertions.assertEquals(null, dto.getMerchant());
    Assertions.assertEquals(1, dto.getVersion());
    dto.fixField("uid");
    dto.fixField("name");
    dto.fixField("merchant");
    dto.fixField("version");
    Assertions.assertNull(dto.getUid());
    Assertions.assertNull(dto.getName());
    Assertions.assertNull(dto.getMerchant());
    Assertions.assertNull(dto.getVersion());
  }
}
