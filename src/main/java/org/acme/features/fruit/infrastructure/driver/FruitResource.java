package org.acme.features.fruit.infrastructure.driver;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.fruit.application.usecase.ListFruitsUsecase;
import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.interaction.query.FruitList;
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
import lombok.RequiredArgsConstructor;

@Path("/fruit")
@RequestScoped
@RequiredArgsConstructor
public class FruitResource {
  private final ListFruitsUsecase fruits;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> get(@Context UriInfo uriInfo) {
    MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
    FruitFilter.FruitFilterBuilder filter = FruitFilter.builder();
    FruitCursor.FruitCursorBuilder cursor = FruitCursor.builder();
    if (queryParams.containsKey("search")) {
      filter = filter.like(queryParams.getFirst("search"));
    }
    cursor = cursor.limit(20);
    Actor actor = null;
    Connection connection = null;
    return fruits.fruits(FruitList.builder().actor(actor).connection(connection)
        .filter(filter.build()).cursor(cursor.build()).build())
        .map(items -> Response.ok(items.getFruits()).build());
  }
}
