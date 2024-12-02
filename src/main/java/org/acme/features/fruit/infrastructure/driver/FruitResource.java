package org.acme.features.fruit.infrastructure.driver;

import org.acme.common.security.Actor;
import org.acme.common.security.Connection;
import org.acme.features.fruit.application.usecase.ListFruitsUsecase;
import org.acme.features.fruit.domain.interaction.FruitCursor;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import org.acme.features.fruit.domain.interaction.query.FruitList;
import org.jboss.logging.Logger;

import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Tracer;
import io.quarkus.security.identity.SecurityIdentity;
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

@Path("/fruit")
@RequestScoped
public class FruitResource {
  private static final Logger LOG = Logger.getLogger(FruitResource.class);

  private final ListFruitsUsecase fruits;
  private final SecurityIdentity security;
  private final LongCounter counter;
  private final Tracer tracer;

  public FruitResource(ListFruitsUsecase fruits, Tracer tracer, Meter meter,
      SecurityIdentity securityIdentity) {
    this.fruits = fruits;
    this.tracer = tracer;
    this.security = securityIdentity;
    this.counter = meter.counterBuilder("hello-metrics").setDescription("hello-metrics")
        .setUnit("invocations").build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<Response> get(@Context UriInfo uriInfo) {
    LOG.error("hello");
    var spanBuilder = this.tracer.spanBuilder("Colores").startSpan();
    try {
      counter.add(1);
      if (null == this.security.getPrincipal()) {
        System.out.println("No autenticado");
      } else {
        System.out.println("Principla" + this.security.getPrincipal().getName());
      }
      MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
      FruitFilter.FruitFilterBuilder filter = FruitFilter.builder();
      FruitCursor.FruitCursorBuilder cursor = FruitCursor.builder();
      if (queryParams.containsKey("search")) {
        filter = filter.like(queryParams.getFirst("search"));
      }
      cursor = cursor.limit(40);
      Actor actor = null;
      Connection connection = null;
      return fruits.fruits(FruitList.builder().actor(actor).connection(connection)
          .filter(filter.build()).cursor(cursor.build()).build())
          .map(items -> Response.ok(items.getFruits()).build());
    } finally {
      spanBuilder.end();
    }
  }
}
