package org.acme.features.fruit.application.accesscontrol;

import org.acme.features.fruit.application.dto.FruitListResultDto;
import org.acme.features.fruit.application.dto.FruitReadResultDto;

import jakarta.enterprise.event.Observes;

public class VisibleFields {

  public void decorateOutput(@Observes FruitReadResultDto result) {
    result.getFruit().setName(result.getFruit().getName() + " y pan");
  }

  public void decorateList(@Observes FruitListResultDto list) {
    System.out.println("\t\tBefore a filter a" + list.size());
    list.getFruits().removeIf(fruit -> fruit.getName().endsWith("azul"));
    System.out.println("\t\tAfter" + list.size());
  }
}
