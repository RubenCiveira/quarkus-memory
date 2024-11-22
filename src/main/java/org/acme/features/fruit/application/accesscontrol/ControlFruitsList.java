package org.acme.features.fruit.application.accesscontrol;

import org.acme.common.security.Allow;
import org.acme.features.fruit.application.usecase.list.FruitsListAllow;
import org.acme.features.fruit.application.usecase.retrieve.FruitsRetrieveAllow;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class ControlFruitsList {

  public void onCheckListAllow(@Observes @FruitsListAllow Allow allowd) {
    System.out.println("Check list allow");
    allowd.setDescription("Goool");
  }

  public void onCheckRetrieveAllow(@Observes @FruitsRetrieveAllow Allow allowd) {
    System.out.println("Check retrieve allow");
  }
}
