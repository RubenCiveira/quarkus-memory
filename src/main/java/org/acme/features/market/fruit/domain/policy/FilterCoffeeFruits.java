package org.acme.features.market.fruit.domain.policy;

import org.acme.features.market.fruit.domain.interaction.result.FruitListResult;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class FilterCoffeeFruits {

  public void filterCoffeeFruits(@Observes FruitListResult list) {
    System.out.println("\t\tFiter coffe, before" + list.size());
    list.getFruits().removeIf(fruit -> fruit.getName().contains("Coffee"));
    System.out.println("\t\tFiter coffe, after" + list.size());
  }
}
