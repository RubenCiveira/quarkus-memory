package org.acme.features.market.fruit.infrastructure.driver;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.market.fruit.domain.Fruits;
import org.acme.features.market.fruit.domain.interaction.FruitCursor;
import org.acme.features.market.fruit.domain.interaction.FruitFilter;
import org.acme.features.market.fruit.domain.interaction.query.ListQuery;
import org.jboss.logging.Logger;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/api/direct-market/fruit")
@RequestScoped
public class FruitResource {
  private static final Logger LOG = Logger.getLogger(FruitResource.class);

  private final Fruits fruits;

  public FruitResource(Fruits fruits) {
    this.fruits = fruits;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> get(@Context UriInfo uriInfo) {
    LOG.error("hello");
    MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
    FruitFilter.FruitFilterBuilder filter = FruitFilter.builder();
    FruitCursor.FruitCursorBuilder cursor = FruitCursor.builder();
    if (queryParams.containsKey("search")) {
      // filter = filter.like(queryParams.getFirst("search"));
    }
    cursor = cursor.limit(40);
    Actor actor = null;
    Connection connection = null;
    return fruits.list(ListQuery.builder().actor(actor).connection(connection)
        .filter(filter.build()).cursor(cursor.build()).build())
        .map(items -> Response.ok(items.getFruits()).build());
  }
}
