package org.acme.features.market.fruit.application.policy;

import org.acme.features.market.fruit.application.interaction.visibility.FruitListableContent;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class FilterCoffeeFruits {

  public void filterCoffeeFruits(@Observes FruitListableContent list) {
    list.getListables().thenApply(ll -> {
      System.out.println("\t\tFiter coffe, before" + ll.size());
      ll.removeIf(fruit -> !(fruit.getName().getValue().contains("Coffee")
          || fruit.getName().getValue().contains("Wine")
          || fruit.getName().getValue().contains("Vinegar")));
      System.out.println("\t\tFiter coffe, after" + ll.size());
      return ll;
    });
  }
}
