package org.acme.features.market.medal.application;

import java.util.Optional;

import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.valueobject.MedalNameVO;
import org.acme.features.market.medal.domain.model.valueobject.MedalUidVO;
import org.acme.features.market.medal.domain.model.valueobject.MedalVersionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MedalDtoUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_medal_dto_builder() {
    Medal entity = Medal.builder().uid(MedalUidVO.from("one")).name(MedalNameVO.from("one"))
        .version(MedalVersionVO.from(1)).build();
    Medal fixRef = Medal.builder().uid(MedalUidVO.from("two")).name(MedalNameVO.from("two"))
        .version(MedalVersionVO.from(2)).build();
    Medal other;
    MedalDto dto = MedalDto.from(entity);
    Assertions.assertEquals("one", dto.getUid().getValue());
    Assertions.assertEquals("one", dto.getName().getValue());
    Assertions.assertEquals(1, dto.getVersion().getValue().orElse(null));
    other = dto.toEntityBuilder(Optional.empty()).build();
    dto.hideField("-");
    dto.fixField("-", fixRef);
    Assertions.assertEquals("one", other.getUid().getValue());
    Assertions.assertEquals("one", other.getName().getValue());
    Assertions.assertEquals(1, other.getVersion().getValue().orElse(null));
    dto.fixField("uid", fixRef);
    dto.fixField("name", fixRef);
    dto.fixField("version", fixRef);
    other = dto.toEntityBuilder(Optional.empty()).build();
    Assertions.assertEquals("two", other.getUid().getValue());
    Assertions.assertEquals("two", other.getName().getValue());
    Assertions.assertEquals(2, other.getVersion().getValue().orElse(null));
    dto.hideField("uid");
    dto.hideField("name");
    dto.hideField("version");
    Assertions.assertNull(dto.getUid());
    Assertions.assertNull(dto.getName());
    Assertions.assertNull(dto.getVersion());
    dto = MedalDto.from(entity);
    Assertions.assertEquals("one", dto.getUid().getValue());
    Assertions.assertEquals("one", dto.getName().getValue());
    Assertions.assertEquals(1, dto.getVersion().getValue().orElse(null));
    dto.fixField("uid");
    dto.fixField("name");
    dto.fixField("version");
    Assertions.assertNull(dto.getUid());
    Assertions.assertNull(dto.getName());
    Assertions.assertNull(dto.getVersion());
  }
}
