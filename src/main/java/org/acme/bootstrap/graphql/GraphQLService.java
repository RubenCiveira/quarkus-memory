package org.acme.bootstrap.graphql;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.acme.common.projection.ExecutionPlan;
import org.acme.common.projection.ExecutionTree;
import org.acme.common.projection.ProjectionRunner;
import org.acme.common.projection.SelfProjection;
import org.acme.features.market.area.infrastructure.bootstrap.AreaProjectionDescriptor.AreaExecutionPlanner;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/test/graphql")
@RequiredArgsConstructor
public class GraphQLService {

  private final ProjectionRunner runner;
  private final SelfProjection self;
  private final Tracer tracer;

  @GET()
  public Object testQl(@Context HttpHeaders httpHeaders, @Context UriInfo info) {
    log.warn("Entrada de control en el log");
    Map<String, String> query = info.getQueryParameters().entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(",", entry.getValue())));

    ExecutionTree tree = self.getExecutionTree();

    Span span = tracer.spanBuilder("My custom span").setAttribute("attr", "Colorines de aqui")
        .setParent(io.opentelemetry.context.Context.current().with(Span.current())).startSpan();
    ExecutionPlan plan = ExecutionPlan.builder().path("/api/market/areas").tree(tree)
        .selection(new AreaExecutionPlanner().withUid("codigo").withName("nombre")
            .withPlace(ofPlace -> ofPlace.withName("place.name").withMerchant("jefe",
                ofMerchant -> ofMerchant.withName("merchant.name")))
            .build())
        .build();
    log.warn("Entrada dentro de un contexto hijo en el log");

    List<Gon> list = runner.list(plan, Gon.class, query, httpHeaders.getRequestHeaders());
    list.forEach(gon -> {
      System.out.println("TENGO UN " + gon.getCodigo());
    });
    span.end();
    return list;
  }

  @GET()
  @Path("/in")
  public Object testId(@Context HttpHeaders httpHeaders, @Context UriInfo info) {
    // Map<String, String> query = info.getQueryParameters().entrySet().stream()
    // .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(",", entry.getValue())));

    ExecutionTree tree = self.getExecutionTree();
    ExecutionPlan plan = ExecutionPlan.builder().path("/api/market/areas/{uid}").tree(tree)
        .selection(new AreaExecutionPlanner().withUid("codigo").withName("nombre")
            .withPlace(ofPlace -> ofPlace.withName("place.name").withMerchant("jefe",
                ofMerchant -> ofMerchant.withName("merchant.name")))
            .build())
        .build();

    List<Gon> list =
        runner.list(plan, Gon.class, Map.of("uid", "1"), httpHeaders.getRequestHeaders());
    list.forEach(gon -> {
      System.out.println("TENGO UN " + gon.getCodigo());
    });
    return list;
  }
}


@Data
@RegisterForReflection
class Gon {
  private String codigo;
  private String nombre;
  private GonPlace place;
  private GonPlace merchant;
}


@Data
@RegisterForReflection
class GonPlace {
  private String name;
}
