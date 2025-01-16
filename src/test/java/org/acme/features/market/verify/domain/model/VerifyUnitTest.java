package org.acme.features.market.verify.domain.model;

import java.util.Optional;

import org.acme.features.market.verify.domain.model.valueobject.VerifyNameVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyUidVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyVersionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VerifyUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test a entity reference contruction")
  void test_verify_builder() {
    Verify entity = Verify.builder().uid(VerifyUidVO.from("one")).name(VerifyNameVO.from("one"))
        .version(VerifyVersionVO.from(1)).build();
    Assertions.assertEquals("one", entity.getUid().getValue());
    Assertions.assertEquals("one", entity.getUidValue());
    Assertions.assertEquals("one", entity.getName().getValue());
    Assertions.assertEquals("one", entity.getNameValue());
    Assertions.assertEquals(1, entity.getVersion().getValue().orElse(null));
    Assertions.assertEquals(1, entity.getVersionValue().orElse(null));
    Assertions.assertEquals("two", entity.withUidValue("two").getUidValue());
    Assertions.assertEquals("two", entity.withNameValue("two").getNameValue());
    Assertions.assertEquals(2,
        entity.withVersionValue(Optional.of(2)).getVersionValue().orElse(null));
    Assertions.assertNull(entity.withVersionValue(Optional.empty()).getVersionValue().orElse(null));
    Assertions.assertNull(entity.withEmptyVersion().getVersionValue().orElse(null));
    Assertions.assertEquals(entity,
        Verify.builder().uidValue("one").nameValue("one").versionValue(1).build());
  }
}
