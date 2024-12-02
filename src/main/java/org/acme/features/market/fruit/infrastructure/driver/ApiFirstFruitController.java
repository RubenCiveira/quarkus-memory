package org.acme.features.market.fruit.infrastructure.driver;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.market.fruit.domain.Fruits;
import org.acme.features.market.fruit.domain.interaction.FruitCursor;
import org.acme.features.market.fruit.domain.interaction.FruitFilter;
import org.acme.features.market.fruit.domain.interaction.query.ListQuery;
import org.acme.openapi.api.FruitApi;
import org.acme.openapi.model.Fruit;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class ApiFirstFruitController implements FruitApi {
  private static final Logger LOG = Logger.getLogger(ApiFirstFruitController.class);

  private final Fruits fruits;

  public ApiFirstFruitController(Fruits fruits) {
    this.fruits = fruits;
  }

  @Override
  public Response fruitApiCreate(Fruit fruit) {
    return null;
  }

  @Override
  public Response fruitApiDelete(String uid) {
    return null;
  }

  @Override
  public Response fruitApiList(Integer limit) {
    FruitFilter.FruitFilterBuilder filter = FruitFilter.builder();
    FruitCursor.FruitCursorBuilder cursor = FruitCursor.builder();
    cursor = cursor.limit(limit);
    Actor actor = null;
    Connection connection = null;
    return fruits
        .list(ListQuery.builder().actor(actor).connection(connection).filter(filter.build())
            .cursor(cursor.build()).build())
        .map(items -> Response.ok(items.getFruits()).build()).await().indefinitely();
  }

  @Override
  public Response fruitApiRetrieve(String uid) {
    return null;
  }

  @Override
  public Response fruitApiUpdate(String uid, Fruit fruit) {
    return null;
  }

}
