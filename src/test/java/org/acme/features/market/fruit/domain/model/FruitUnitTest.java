package org.acme.features.market.fruit.domain.model;

import org.acme.common.exception.ConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FruitUnitTest {

    /**
     * @autogenerated ValueObjectGenerator
     */
    @Test
    @DisplayName("Test a entity reference contruction")
    void test_fruit_builder() {
        Fruit entity = Fruit.builder().uidValue("one").nameValue("one").versionValue(1).build();
        Assertions.assertEquals("one", entity.getUid().getValue());
        Assertions.assertEquals("one", entity.getName().getValue());
        Assertions.assertEquals(1, entity.getVersion().getValue().orElse(null));
        entity.setUid("two");
        Assertions.assertEquals("two", entity.getUid().getValue());
        entity.setName("two");
        Assertions.assertEquals("two", entity.getName().getValue());
        entity.setVersion(2);
        Assertions.assertEquals(2, entity.getVersion().getValue().orElse(null));
    }
}
