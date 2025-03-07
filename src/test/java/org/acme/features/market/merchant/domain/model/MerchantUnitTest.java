package org.acme.features.market.merchant.domain.model;

import java.util.Optional;

import org.acme.features.market.merchant.domain.model.valueobject.MerchantEnabledVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantKeyVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantNameVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantUidVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantVersionVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MerchantUnitTest {

  /**
   * @autogenerated ValueObjectGenerator
   */
  @Test
  @DisplayName("Test Merchant contruction")
  void test_merchant_builder() {
    Merchant entity = Merchant.builder().uid(MerchantUidVO.from("one"))
        .name(MerchantNameVO.from("one")).enabled(MerchantEnabledVO.from(true))
        .key(MerchantKeyVO.from("one")).version(MerchantVersionVO.from(1)).build();
    Assertions.assertEquals("one", entity.getUid().getValue());
    Assertions.assertEquals("one", entity.getUidValue());
    Assertions.assertEquals("one", entity.getName().getValue());
    Assertions.assertEquals("one", entity.getNameValue());
    Assertions.assertEquals(true, entity.getEnabled().getValue());
    Assertions.assertEquals(true, entity.getEnabledValue());
    Assertions.assertEquals("one", entity.getKey().getValue().orElse(null));
    Assertions.assertEquals("one", entity.getKeyValue().orElse(null));
    Assertions.assertEquals(1, entity.getVersion().getValue().orElse(null));
    Assertions.assertEquals(1, entity.getVersionValue().orElse(null));
    Assertions.assertEquals("two", entity.withUidValue("two").getUidValue());
    Assertions.assertEquals("two", entity.withNameValue("two").getNameValue());
    Assertions.assertEquals(false, entity.withEnabledValue(false).getEnabledValue());
    Assertions.assertEquals("two",
        entity.withKeyValue(Optional.of("two")).getKeyValue().orElse(null));
    Assertions.assertNull(entity.withKeyValue(Optional.empty()).getKeyValue().orElse(null));
    Assertions.assertNull(entity.withEmptyKey().getKeyValue().orElse(null));
    Assertions.assertEquals(2,
        entity.withVersionValue(Optional.of(2)).getVersionValue().orElse(null));
    Assertions.assertNull(entity.withVersionValue(Optional.empty()).getVersionValue().orElse(null));
    Assertions.assertNull(entity.withEmptyVersion().getVersionValue().orElse(null));
    Assertions.assertEquals(entity, Merchant.builder().uidValue("one").nameValue("one")
        .enabledValue(true).keyValue("one").versionValue(1).build());
  }
}
