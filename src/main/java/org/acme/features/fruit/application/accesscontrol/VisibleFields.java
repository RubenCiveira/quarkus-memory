package org.acme.features.fruit.application.accesscontrol;

import org.acme.features.fruit.application.dto.FruitListResultDto;
import org.acme.features.fruit.application.dto.FruitReadResultDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class VisibleFields {

  public void decorateOutput(@Observes FruitReadResultDto result) {
    result.getFruit().setName(result.getFruit().getName() + " y pan");
  }

  public void decorateList(@Observes FruitListResultDto list) {
    list.getFruits().removeIf(fruit -> !fruit.getName().endsWith("azul"));
  }
}
