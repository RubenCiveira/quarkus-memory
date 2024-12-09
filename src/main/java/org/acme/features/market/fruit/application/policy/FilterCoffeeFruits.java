package org.acme.features.market.fruit.application.policy;

import org.acme.features.market.fruit.application.usecase.service.FruitsVisibilityService.FruitListableContent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class FilterCoffeeFruits {

  public void filterCoffeeFruits(@Observes FruitListableContent list) {
    System.out.println("\t\tFiter coffe, before" + list.size());
    list.removeIf(fruit -> !(fruit.getName().getValue().contains("Coffee")
        || fruit.getName().getValue().contains("Wine")
        || fruit.getName().getValue().contains("Vinegar")));
    System.out.println("\t\tFiter coffe, after" + list.size());
  }
}
