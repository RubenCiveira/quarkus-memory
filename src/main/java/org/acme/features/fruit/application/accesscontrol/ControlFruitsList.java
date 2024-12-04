package org.acme.features.fruit.application.accesscontrol;

import org.acme.features.fruit.application.usecase.ListAllow;
import org.acme.features.fruit.application.usecase.RetrieveAllow;

import jakarta.enterprise.event.Observes;

public class ControlFruitsList {

  public void onCheckListAllow(@Observes ListAllow allowd) {
    System.out.println("Check list allow");
    allowd.setDescription("Goool");
  }

  public void onCheckRetrieveAllow(@Observes RetrieveAllow allowd) {
    System.out.println("Check retrieve allow");
  }
}
